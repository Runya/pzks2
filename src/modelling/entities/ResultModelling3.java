package modelling.entities;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;

import java.util.*;


public class ResultModelling3 extends ResultModelling {
    public ResultModelling3(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue) {
        super(taskGraph, computerSystem, queue);
    }

    @Override
    public void model() {
        initialStep();//step 0
        int step = 0;
        boolean isSomeActionOnCycle;



        while (isNonCompletedTasks()) {
            ModellingTask readyTask;
            List<ModellingProcessor> freeProcessors;
            do {
                isSomeActionOnCycle = false;
                readyTask = defineReadyTask();//choose ready task
                freeProcessors = getFreeProcessors();//get free processors
                if (readyTask != null && freeProcessors.size() > 0) {
                    //define processor
                    ModellingProcessor freeProcessor = defineProcessor(freeProcessors, readyTask);
                    //send data
                    if (freeProcessor.haveAllData(readyTask)) {
                        freeProcessor.setCurrentTask(readyTask);
                        isSomeActionOnCycle = true;
                    } else {
                        //sending data
                        List<ModellingTask> receiveDataFromTasks = freeProcessor.needReceiveData(getDependsFrom(readyTask));
                        //define receive from processors
                        for (ModellingTask dataFromTask : receiveDataFromTasks) {
                            ModellingProcessor fromProcessor = getNearestProcessorWithData(freeProcessor, dataFromTask)[0];
                            ModellingProcessor sendToProcessor = getNearestProcessorWithData(freeProcessor, dataFromTask)[1];
                            int sendReceiveTime = defineSendTime(dataFromTask, readyTask, fromProcessor, sendToProcessor);
                            fromProcessor.sendData(dataFromTask, readyTask, sendToProcessor, sendReceiveTime);
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

    private List<ModellingProcessor> getFreeProcessors() {
        List<ModellingProcessor> freeProcessors = new ArrayList<>();
        for (ModellingProcessor processor : processors) {
            if (processor.getState() == ModellingProcessor.State.FREE)
                freeProcessors.add(processor);
        }

        freeProcessors.sort(Comparator.comparingInt(o -> - o.getPriority()));

        return freeProcessors;
    }

    private ModellingProcessor defineProcessor(List<ModellingProcessor> freeProcessors, ModellingTask task) {
        ModellingProcessor processor = null;
        if (freeProcessors.size() == 1) return freeProcessors.get(0);
        List<ModellingTask> dependsFrom = task.getDependsFrom();
        Map<ModellingProcessor, Integer> processorsWay = new HashMap<>();
        for (ModellingProcessor freeProcessor : freeProcessors) {
            if (freeProcessor.haveAllData(task)) return freeProcessor;
            Map<ModellingProcessor, Double> receiveWeight = new HashMap<>();
            //define all processors with need data
            List<ModellingProcessor> processorsWithNeedData = new ArrayList<>();
            for (ModellingTask modellingTask : dependsFrom) {
                ModellingProcessor[] nearestProcessorWithData = getNearestProcessorWithData(freeProcessor, modellingTask);
//                if (!processorsWithNeedData.contains(nearestProcessorWithData[0]))
                processorsWithNeedData.add(nearestProcessorWithData[0]);
                receiveWeight.put(nearestProcessorWithData[0],
                        taskGraph.getConnectionBandwidth(modellingTask.getTask().getId(), task.getTask().getId()));
            }
            //define min distances sum
            int sumWay = 0;
            for (ModellingProcessor procWithData : processorsWithNeedData) {
                sumWay += computerSystem.getMinWayValue(freeProcessor.getProcessor(), procWithData.getProcessor()) *
                        receiveWeight.get(procWithData);
//                double receiveFromTaskWeight = receiveWeight.get(procWithData);
//                sumWay *= receiveFromTaskWeight;
            }
//            processorsWay.put(freeProcessor, sumWay);
//            sumWay *= receiveFromTaskWeight;
            processorsWay.put(freeProcessor, sumWay);
        }
        //define min way
        int maxPriority = Integer.MIN_VALUE;
//        for (ModellingProcessor modellingProcessor : processorsWay.keySet()) {
//            if (processorsWay.get(modellingProcessor) < minWay) {
//                minWay = processorsWay.get(modellingProcessor);
//                processor = modellingProcessor;
//            }
//        }
        for (ModellingProcessor freeProcessor : freeProcessors) {
            if (freeProcessor.getPriority() > maxPriority) {
                maxPriority = freeProcessor.getPriority();
                processor = freeProcessor;
            }
        }
        return processor;
    }

    private ModellingProcessor[] getNearestProcessorWithData(ModellingProcessor freeProcessor, ModellingTask dataFromTask) {
        ModellingProcessor[] fromTo = new ModellingProcessor[2];
        ModellingProcessor processorWithData = null;
        ModellingProcessor processorToReceiveData = null;
        for (ModellingProcessor processor : processors) {
            if (processor.completedThisTask(dataFromTask)) {
                processorWithData = processor;
                break;
            }
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
}
