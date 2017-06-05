package modelling.entities;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;

import java.util.List;

/**
 * Created by vadim on 10.05.14.
 */
public class ResultModelling2 extends ResultModelling {

    public ResultModelling2(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue) {
        super(taskGraph, computerSystem, queue);
    }

    @Override
    public void model() {
        initialStep();//step 0
        int step = 0;
        boolean isSomeActionOnCycle;
        while (isNonCompletedTasks()) {
            ModellingTask readyTask;
            ModellingProcessor freeProcessor;
            do {
                isSomeActionOnCycle = false;
                readyTask = defineReadyTask();//choose ready task
                freeProcessor = defineFreeProcessor();//choose free processor
                if (readyTask != null && freeProcessor != null) {
                    if (freeProcessor.haveAllData(readyTask)) {
                        freeProcessor.setCurrentTask(readyTask);
                        isSomeActionOnCycle = true;
                    } else {
                        //sending data
                        List<ModellingTask> receiveDataFromTasks = freeProcessor.needReceiveData(getDependsFrom(readyTask));
                        //define receive from processors
                        for (ModellingTask dataFromTask : receiveDataFromTasks) {
                            ModellingProcessor fromProcessor = getNearestProcessorWithData(freeProcessor, dataFromTask, readyTask)[0];
                            ModellingProcessor sendToProcessor = getNearestProcessorWithData(freeProcessor, dataFromTask, readyTask)[1];
                            int sendReceiveTime = defineSendTime(dataFromTask, readyTask, fromProcessor, sendToProcessor);
                            fromProcessor.sendData(dataFromTask, readyTask, sendToProcessor, sendReceiveTime);
//                            if (fromProcessor.sendData(dataFromTask, readyTask, sendToProcessor, sendReceiveTime))
//                                sendToProcessor.receiveData(dataFromTask, readyTask, fromProcessor, sendReceiveTime);
                        }
                    }
                }
            }
            while (isSomeActionOnCycle);
            setGantViewStepState(step);//update gant view
//            printDataInProcessors(step);
            // modify step processor state
            for (ModellingProcessor processor : processors) {
                ModellingTask task = processor.executeStep();
                if (task != null)//observer
                    for (ModellingTask modellingTask : tasks) {
                        modellingTask.taskCompleted(task);
                    }
            }
            step++;
        }
    }

    private ModellingProcessor[] getNearestProcessorWithData(ModellingProcessor freeProcessor, ModellingTask dataFromTask,
                                                             ModellingTask readyTask) {
        ModellingProcessor[] fromTo = new ModellingProcessor[2];
        ModellingProcessor processorWithData = null;
        ModellingProcessor processorToReceiveData = null;
        for (ModellingProcessor processor : processors) {
            if (processor.completedThisTask(dataFromTask)) {
                processorWithData = processor;
                break;
            }
        }
        if (processorWithData == null) {
            taskGraph.saveToFile("wrong_graph");
        }
        int[] processorsChain = computerSystem.getMinWay(processorWithData.getProcessor().getId(), freeProcessor.getProcessor().getId());
        for (int i = 0; i < processorsChain.length - 1; i++) {
            ModellingProcessor curProcessor = getProcessorById(processorsChain[i]);
            if (curProcessor.completedThisTask(dataFromTask)) {
                processorWithData = curProcessor;
                processorToReceiveData = getProcessorById(processorsChain[i + 1]);
            }
        }
        fromTo[0] = processorWithData;
        fromTo[1] = processorToReceiveData;
        return fromTo;
    }

    private ModellingProcessor defineFreeProcessor() {
        ModellingProcessor freeProcessor = null;
        int freeTime = 0;
        for (ModellingProcessor processor : processors) {
            if (processor.getState() == ModellingProcessor.State.FREE) {
                if (freeProcessor == null) {
                    freeProcessor = processor;
                    freeTime = processor.getFreeTime();
                    continue;
                }
                if (processor.getFreeTime() >= freeTime) {
                    if (processor.getFreeTime() == freeTime & (processor.getPriority() > freeProcessor.getPriority())) {
                        freeProcessor = processor;
                        continue;
                    }
                    if (processor.getFreeTime() > freeTime) {
                        freeTime = processor.getFreeTime();
                        freeProcessor = processor;
                    }
                }
            }
        }
        return freeProcessor;
    }

    private void removeTaskFromProcessor(ModellingTask modellingTask) {
        for (ModellingProcessor processor : processors) {
            if (processor.getState() == ModellingProcessor.State.BUSY && processor.getCurrentTask().equals(modellingTask)) {
                processor.setCurrentTask(null);
                processor.setState(ModellingProcessor.State.FREE);
            }
        }
    }

    private ModellingProcessor getProcessorWithData(ModellingTask task) {
        for (ModellingProcessor processor : processors) {
            if (processor.completedThisTask(task)) return processor;
        }
        return null;
    }

    private ModellingTask getTaskById(int id) {
        for (ModellingTask task : tasks) {
            if (task.getTask().getId() == id) return task;
        }
        return null;
    }

    private boolean isProcessorBusy(ModellingProcessor processor, boolean inputOutputProcessor) {
        if (processor.getState() == ModellingProcessor.State.BUSY) return true;
        if (inputOutputProcessor) {
            for (ModellingConnection modellingConnection : processor.getPhysicalLinks()) {
                if (modellingConnection.isSomeSendOrReceive()) return true;
            }
        } else {
            return processor.getState() == ModellingProcessor.State.BUSY;
        }
        return false;
    }

    private void printDataInProcessors(int step) {
        System.out.println("Step " + step);
        for (ModellingProcessor processor : processors) {
            System.out.print("Processor " + processor.getProcessor().getId() + "\n[");
            for (ModellingTask task : processor.getThisProcessorTasks()) {
                System.out.print(task.getTask().getId() + ",");
            }
            System.out.println("]");
        }
        System.out.println("------------------------");
    }
}
