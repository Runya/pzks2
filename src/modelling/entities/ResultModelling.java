package modelling.entities;

import entities.computerSystem.ComputerSystem;
import entities.computerSystem.Processor;
import entities.computerSystem.SystemConnection;
import entities.task.Task;
import entities.task.TaskGraph;
import modelling.GantViewEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by vadim on 10.05.14.
 */
public abstract class ResultModelling {
    protected final TaskGraph taskGraph;
    protected final ComputerSystem computerSystem;
    protected final int[] queue;

    protected List<ModellingTask> tasks;
    protected List<ModellingProcessor> processors;
    protected List<GantViewEntity> gantView;

    protected ModellingProcessor[] initialProcessorsQueue;

    public ResultModelling(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue) {
        this.taskGraph = taskGraph;
        this.computerSystem = computerSystem;
        this.queue = queue;

        tasks = new ArrayList<>();
        for (Task task : taskGraph.getTasks()) {
            tasks.add(new ModellingTask(task, defineStartTaskState(task)));
        }

        for (ModellingTask task : tasks) {
            task.setDependsFrom(defineDependency(taskGraph, task));
        }
        processors = new ArrayList<>();
        for (Processor processor : computerSystem.getProcessors()) {
            processors.add(new ModellingProcessor(processor, defineProcessorPriority(processor, computerSystem)));
        }
        setUpNewGantView();
        defineStartProcessorQueue();
    }

    private List<ModellingTask> defineDependency(TaskGraph taskGraph, ModellingTask task) {
        int[] dependFromTasksIds = taskGraph.getFatherTasksIds(task.getTask());
        List<ModellingTask> modellingTasks = new ArrayList<>();
        for (int taskId : dependFromTasksIds) {
            ModellingTask taskById = getTaskById(taskId);
            if (!modellingTasks.contains(taskById)) {
                modellingTasks.add(taskById);
            }
        }
        return modellingTasks;
    }

    private ModellingTask getTaskById(int id) {
        for (ModellingTask task : tasks) {
            if (task.getTask().getId() == id) {
                return task;
            }
        }
        return null;
    }

    private ModellingTask.State defineStartTaskState(Task task) {
        int[][] adjacencyMatrix = taskGraph.getAdjacencyMatrix();
        int pos = taskGraph.getTaskPosById(task.getId());
//        for (int[] ints : adjacencyMatrix) {
//            if (ints[pos] == 1) return ModellingTask.State.NOT_READY;
//        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[i][pos] == 1) return ModellingTask.State.NOT_READY;
        }
        return ModellingTask.State.READY;
    }

    abstract public void model();

    public boolean isNonCompletedTasks() {
        for (ModellingTask task : tasks) {
            if (task.getState() != ModellingTask.State.COMPLETED) return true;
        }
        return false;
    }

    private void setUpNewGantView() {
        this.gantView = new ArrayList<>();
        GantViewEntity gantViewEntity;
        //add processors to gant chart
        for (ModellingProcessor processor : processors) {
            gantViewEntity = new GantViewEntity(processor, GantViewEntity.GantEntityType.PROCESSOR);
            gantView.add(gantViewEntity);
            //add connections to gant chart
            for (int i = 0; i < SystemConnection.DEFAULT_PHYSICAL_LINK_NUMBER; i++) {
                ModellingConnection modellingConnection = new ModellingConnection(processor);
                gantViewEntity = new GantViewEntity(processor, modellingConnection, GantViewEntity.GantEntityType.CONNECTION, i);
                processor.addPhysicalLinks(modellingConnection);
                gantView.add(gantViewEntity);
            }
        }
    }

    public List<GantViewEntity> getGantView() {

//        this.gantView = new ArrayList<>();
//
//        GantViewEntity gantViewEntity1 = new GantViewEntity(processors.get(0), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        gantView.add(gantViewEntity1);
//
//        GantViewEntity gantViewEntity1L1 = new GantViewEntity(processors.get(0), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity1L1.addStateStep("1-2", 4);
//        gantViewEntity1L1.addStateStep("1-2");
//        gantView.add(gantViewEntity1L1);
//        //--------
//        GantViewEntity gantViewEntity2 = new GantViewEntity(processors.get(0), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantView.add(gantViewEntity2);
//
//        GantViewEntity gantViewEntity2L1 = new GantViewEntity(processors.get(0), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity2L1.addStateStep("1-2", 4);
//        gantViewEntity2L1.addStateStep("1-2");
//        gantViewEntity2L1.addStateStep("2-3", 6);
//        gantView.add(gantViewEntity2L1);
//        //--------
//
//        GantViewEntity gantViewEntity3L1 = new GantViewEntity(processors.get(0), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity3L1.addStateStep("2-3", 6);
//        gantView.add(gantViewEntity3L1);
        return gantView;
    }

    protected void setGantViewStepState(int step) {
        String state = "";
        for (GantViewEntity gantViewEntity : gantView) {
            switch (gantViewEntity.gantEntityType) {
                case PROCESSOR:
                    ModellingTask modellingTask = gantViewEntity.getProcessor().getCurrentTask();
                    state = gantViewEntity.getProcessor().getState() == ModellingProcessor.State.BUSY ?
                            modellingTask.getTask().getId() + "" : GantViewEntity.SIMPLE_WORK_STATE;
//                    gantViewEntity.addStateStep(state, step);
                    break;
                case CONNECTION:
                    ModellingConnection modellingConnection = gantViewEntity.getConnection();
                    if (modellingConnection.getState1() == ModellingConnection.State.FREE &
                            modellingConnection.getState2() == ModellingConnection.State.FREE) {
                        state = GantViewEntity.SIMPLE_WORK_STATE;
                    } else {
                        if (modellingConnection.getTaskFrom_1() != null) {
//                            if (modellingConnection.getDirection1() == ModellingConnection.Direction.SEND) {
//                                state = "S:" + modellingConnection.getTaskFrom_1().getTask().getId() + "-" + modellingConnection.getTaskTo_1().getTask().getId();
//                                state += "(" + modellingConnection.getConnectedToProcessor().getProcessor().getId() + ")";
//                            } else {
//                                state = GantViewEntity.SIMPLE_WORK_STATE;
//                            }
                            state = modellingConnection.getDirection1() == ModellingConnection.Direction.SEND ?
                                    "S:" + modellingConnection.getTaskFrom_1().getTask().getId() + "-" + modellingConnection.getTaskTo_1().getTask().getId() :
                                    "R:" + modellingConnection.getTaskFrom_1().getTask().getId() + "-" + modellingConnection.getTaskTo_1().getTask().getId();
//                            state += "(" + modellingConnection.getConnectedToProcessor().getProcessor().getId() + ")";
                        } else {
                            state = GantViewEntity.SIMPLE_WORK_STATE;
                        }

                        //define duplex
                        if (SystemConnection.DUPLEX & modellingConnection.getTaskFrom_2() != null) {
//                            if (modellingConnection.getDirection2() == ModellingConnection.Direction.SEND) {
                            state += " / ";
//                                state += "S:" + modellingConnection.getTaskFrom_2().getTask().getId() + "-" + modellingConnection.getTaskTo_2().getTask().getId();
//                                state += "(" + modellingConnection.getConnectedToProcessor().getProcessor().getId() + ")";
//                            } else {
//                                state = GantViewEntity.SIMPLE_WORK_STATE;
//                            }
                            state += modellingConnection.getDirection2() == ModellingConnection.Direction.SEND ?
                                    "S:" + modellingConnection.getTaskFrom_2().getTask().getId() + "-" + modellingConnection.getTaskTo_2().getTask().getId() :
                                    "R:" + modellingConnection.getTaskFrom_2().getTask().getId() + "-" + modellingConnection.getTaskTo_2().getTask().getId();
//todo null pointer                            state += "(" + modellingConnection.getConnectedToProcessor().getProcessor().getId() + ")";
                        }
                    }
                    break;
            }
            gantViewEntity.addStateStep(state, step);
        }
    }

    private int defineProcessorPriority(Processor processor, ComputerSystem computerSystem) {
        return computerSystem.getProcessorConnectivity(processor);
    }

    private void defineStartProcessorQueue() {
        initialProcessorsQueue = new ModellingProcessor[processors.size()];
        for (int i = 0; i < processors.size(); i++) {
            initialProcessorsQueue[i] = processors.get(i);
        }
        Arrays.sort(initialProcessorsQueue, (processor1, processor2) -> processor2.getPriority() - processor1.getPriority());
    }

    protected void initialStep() {
        for (int i = 0; i < initialProcessorsQueue.length; i++) {
            ModellingProcessor processor = initialProcessorsQueue[i];
            ModellingTask task = defineReadyTask();
            if (task != null) {
                processor.setCurrentTask(task);
            } else break;
        }
    }

    protected ModellingTask defineReadyTask() {
        ModellingTask readyTask = null;
        for (int i : queue) {
            for (ModellingTask task : tasks) {
                if (task.getTask().getId() == i & task.getState() == ModellingTask.State.READY) {
                    readyTask = task;
                    break;
                }
            }
            if (readyTask != null) break;
        }
        return readyTask;
    }

    protected ModellingProcessor getProcessorById(int id) {
        for (ModellingProcessor processor : processors) {
            if (processor.getProcessor().getId() == id)
                return processor;
        }
        return null;
    }

    protected List<ModellingTask> getDependsFrom(ModellingTask modellingTask) {
        List<ModellingTask> dependsFromMTasks = new ArrayList<>();
        Set<Integer> dependsFrom = modellingTask.getTask().getDependsFrom();
        for (Integer integer : dependsFrom) {
            //find modelling task
            for (ModellingTask task : tasks) {
                if (task.getTask().getId() == integer)
                    dependsFromMTasks.add(task);
            }
        }
        return dependsFromMTasks;
    }

    protected int defineSendTime(ModellingTask fromTask, ModellingTask toTask, ModellingProcessor fromProcessor, ModellingProcessor toProcessor) {
        double sendWeight = taskGraph.getConnectionBandwidth(fromTask.getTask().getId(), toTask.getTask().getId());
        double connectionBandwidth = computerSystem.getConnectionBandwidth(fromProcessor.getProcessor().getId(), toProcessor.getProcessor().getId());
        return (int) Math.ceil(sendWeight / connectionBandwidth);
    }
}

