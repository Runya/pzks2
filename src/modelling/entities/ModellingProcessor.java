package modelling.entities;

import entities.computerSystem.Processor;
import entities.computerSystem.SystemConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 10.05.14.
 */
public class ModellingProcessor {
    private final Processor processor;
    private State state;
    private final List<ModellingConnection> physicalLinks;
    private int freeTime;
    private ModellingTask currentTask;
    private List<ModellingTask> thisProcessorTasks;
    private int priority;

    public ModellingProcessor(Processor processor, int priority) {
        this.processor = processor;
        state = State.FREE;
        physicalLinks = new ArrayList<>();
        thisProcessorTasks = new ArrayList<>();
        this.priority = priority;
    }

    public Processor getProcessor() {
        return processor;
    }

    public State getState() {
        return state;
    }

    public boolean isFree() {
        if (state == State.FREE) return true;
        if (state == State.RECEIVE_DATA) return false;
        if (Processor.IN_OUT_PROCESSOR && state == State.SEND_DATA) return true;
        return false;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<ModellingConnection> getPhysicalLinks() {
        return physicalLinks;
    }

    public void addPhysicalLinks(ModellingConnection modellingConnection) {
        physicalLinks.add(modellingConnection);
    }

    public int getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(int freeTime) {
        this.freeTime = freeTime;
    }

    public void incFreeTime() {
        this.freeTime++;
    }

    public ModellingTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(ModellingTask currentTask) {
        this.currentTask = currentTask;
        int timeToExecute = (int) Math.ceil(currentTask.getTask().getWeight() / processor.getPower());
        currentTask.setStateAndTime(ModellingTask.State.IN_PROGRESS, timeToExecute);
        state = State.BUSY;
    }

    public List<ModellingTask> needReceiveData(List<ModellingTask> dependsFromMTasks) {
        List<ModellingTask> haventTasks = new ArrayList<>();
        for (ModellingTask dependsFromMTask : dependsFromMTasks) {
            if (!thisProcessorTasks.contains(dependsFromMTask))
                haventTasks.add(dependsFromMTask);
        }
        return haventTasks;
    }

    public boolean completedThisTask(ModellingTask task) {
        return thisProcessorTasks.contains(task);
    }

    private ModellingConnection getFreePhysicalLink(ModellingConnection.Direction direction, ModellingProcessor connectToProcessor) {
        if (!SystemConnection.DUPLEX) {
            for (ModellingConnection physicalLink : physicalLinks) {
                if (physicalLink.getState1() == ModellingConnection.State.FREE)
                    return physicalLink.setDirection1(ModellingConnection.Direction.READY);
            }
        } else {//duplex
            for (ModellingConnection physicalLink : physicalLinks) {
                if (physicalLink.getState1() == ModellingConnection.State.FREE) {
                    if ((physicalLink.getState2() == ModellingConnection.State.FREE) |
                            (physicalLink.getState2() == ModellingConnection.State.BUSY &
                                    physicalLink.getDirection2() != direction
                                    & (physicalLink.getConnectedToProcessor() != null && physicalLink.getConnectedToProcessor().equals(connectToProcessor)))) {
                        return physicalLink.setDirection1(ModellingConnection.Direction.READY);
                    }
                }
                if (physicalLink.getState2() == ModellingConnection.State.FREE) {
                    if ((physicalLink.getState1() == ModellingConnection.State.FREE) |
                            (physicalLink.getState1() == ModellingConnection.State.BUSY &
                                    physicalLink.getDirection1() != direction)
                                    & (physicalLink.getConnectedToProcessor() != null && physicalLink.getConnectedToProcessor().equals(connectToProcessor))) {
                        return physicalLink.setDirection2(ModellingConnection.Direction.READY);
                    }
                }
            }
        }
        return null;
    }

    public ModellingTask executeStep() {
        //execute send receive
        for (ModellingConnection physicalLink : physicalLinks) {
            physicalLink.executeStep();
        }
        //execute tasks
        if (state == State.BUSY) {
            if (currentTask.executeStep()) {
                thisProcessorTasks.add(currentTask);
                state = State.FREE;
                freeTime = 0;
                return currentTask;
            }
        } else freeTime++;
        return null;
    }

    public boolean receiveData(ModellingTask fromTask, ModellingTask toTask, ModellingProcessor fromProcessor, int timeToRun) {
        // check duplicate this sending
        for (ModellingConnection link : physicalLinks) {
            if (link.alreadySendThisData(ModellingConnection.Direction.RECEIVE, fromTask, toTask, fromProcessor))
                return false;
        }
        //define link for receive
        ModellingConnection connection = getFreePhysicalLink(ModellingConnection.Direction.RECEIVE, fromProcessor);
        if (connection == null) return false;
        connection.setReadyStateAndDirectionToBusy(ModellingConnection.State.BUSY,
                ModellingConnection.Direction.RECEIVE, timeToRun, fromTask, toTask, fromProcessor);
//        state = State.RECEIVE_DATA;
        return true;
    }

    public boolean sendData(ModellingTask fromTask, ModellingTask toTask, ModellingProcessor sendToProcessor, int timeToRun) {
        // check duplicate this sending
        for (ModellingConnection link : physicalLinks) {
            if (link.alreadySendThisData(ModellingConnection.Direction.SEND, fromTask, toTask, sendToProcessor))
                return false;
        }
        //define link for send
        ModellingConnection connection = getFreePhysicalLink(ModellingConnection.Direction.SEND, sendToProcessor);
        if (connection == null) return false;
        if (sendToProcessor.receiveData(fromTask, toTask, this, timeToRun))
            connection.setReadyStateAndDirectionToBusy(ModellingConnection.State.BUSY,
                    ModellingConnection.Direction.SEND, timeToRun, fromTask, toTask, sendToProcessor);
        else
            return false;
//        state = State.SEND_DATA;
        return true;
    }

    public void addReceivedData(ModellingTask modellingTask) {
        thisProcessorTasks.add(modellingTask);
    }

    public int getPriority() {
        return priority;
    }

    public boolean haveAllData(ModellingTask task) {
        List<ModellingTask> dependsFrom = task.getDependsFrom();
        for (ModellingTask modellingTask : dependsFrom) {
            if (!thisProcessorTasks.contains(modellingTask)) return false;
        }
        return true;
    }

    public List<ModellingTask> getThisProcessorTasks() {
        return thisProcessorTasks;
    }

    public enum State {
        BUSY, FREE, RECEIVE_DATA, SEND_DATA
    }

    @Override
    public String toString() {
        return "ModellingProcessor{" +
                "processor=" + processor +
                ", state=" + state +
                ", physicalLinks=" + physicalLinks +
                ", freeTime=" + freeTime +
                ", currentTask=" + currentTask +
                '}';
    }
}
