package modelling.entities;

import entities.computerSystem.SystemConnection;
import modelling.GantViewEntity;

/**
 * Created by vadim on 10.05.14.
 */
public class ModellingConnection {
    private final ModellingProcessor processor;
    private ModellingProcessor connectedToProcessor;
    private Direction direction1;
    private Direction direction2;//not uses when non duplex mode
    private State state1;
    private State state2;//not uses when non duplex mode
    private double timeToRun1;
    private double timeToRun2;//not uses when non duplex mode
    private ModellingTask taskFrom_1;
    private ModellingTask taskTo_1;
    private ModellingTask taskFrom_2;
    private ModellingTask taskTo_2;

    public ModellingConnection(ModellingProcessor fatherProcessor) {
        this.processor = fatherProcessor;
        this.state1 = State.FREE;
        this.state2 = State.FREE;
    }

    public Direction getDirection1() {
        return direction1;
    }

    public ModellingConnection setDirection1(Direction direction1) {
        this.direction1 = direction1;
        return this;
    }

    public Direction getDirection2() {
        return direction2;
    }

    public ModellingConnection setDirection2(Direction direction2) {
        this.direction2 = direction2;
        return this;
    }

    public State getState1() {
        return state1;
    }

    public void setState1(State state1) {
        this.state1 = state1;
    }

    public void setReadyStateAndDirectionToBusy(State state, Direction direction, int timeToRun, ModellingTask fromTask, ModellingTask toTask, ModellingProcessor sendToProcessor) {
        if (direction1 != null && direction1 == Direction.READY) {
            setState1(state);
            direction1 = direction;
            timeToRun1 = timeToRun;
            taskFrom_1 = fromTask;
            taskTo_1 = toTask;
            connectedToProcessor = sendToProcessor;
        } else {
            if (direction2 != null && direction2 == Direction.READY) {
                setState2(state);
                direction2 = direction;
                timeToRun2 = timeToRun;
                taskFrom_2 = fromTask;
                taskTo_2 = toTask;
                connectedToProcessor = sendToProcessor;
            }
        }

    }

    public State getState2() {
        return state2;
    }

    public void setState2(State state2) {
        this.state2 = state2;
    }

    public boolean isSomeSendOrReceive() {
        return state1 == State.BUSY | state2 == State.BUSY;
    }

    public void decTimeToRun() {
        if (--timeToRun1 == 0 && direction1 == Direction.RECEIVE) processor.completedThisTask(taskFrom_1);
        if (--timeToRun2 == 0 && direction2 == Direction.RECEIVE) processor.completedThisTask(taskFrom_2);
    }

    public void executeStep() {
        if (--timeToRun1 == 0) {
            state1 = State.FREE;
            if (direction1 == Direction.RECEIVE)
                processor.addReceivedData(taskFrom_1);
            taskFrom_1 = null;
            taskTo_1 = null;
            connectedToProcessor = null;
//            if (direction1 == Direction.RECEIVE)
//                processor.setState(ModellingProcessor.State.FREE);
        }
        if (--timeToRun2 == 0) {
            state2 = State.FREE;
            if (direction2 == Direction.RECEIVE)
                processor.addReceivedData(taskFrom_2);
            taskFrom_2 = null;
            taskTo_2 = null;
            connectedToProcessor = null;
        }
//        if (state1 == State.FREE & state2 == State.FREE & processor.getState() != ModellingProcessor.State.BUSY)
//            processor.setState(ModellingProcessor.State.FREE);
    }

    public ModellingTask getTaskFrom_1() {
        return taskFrom_1;
    }

    public void setTaskFrom_1(ModellingTask taskFrom_1) {
        this.taskFrom_1 = taskFrom_1;
    }

    public ModellingTask getTaskFrom_2() {
        return taskFrom_2;
    }

    public void setTaskFrom_2(ModellingTask taskFrom_2) {
        this.taskFrom_2 = taskFrom_2;
    }

    public String getGantState() {
        String state;
        if (state1 == ModellingConnection.State.FREE &
                state2 == ModellingConnection.State.FREE) {
            state = GantViewEntity.SIMPLE_WORK_STATE;
        } else {
            state = state1 == State.BUSY ?
                    taskFrom_1.getTask().getId() + "-" + taskTo_1.getTask().getId() :
                    GantViewEntity.SIMPLE_WORK_STATE;
            //define duplex
            if (SystemConnection.DUPLEX & state2 == ModellingConnection.State.BUSY) {
                state += " / ";
                state += taskFrom_2.getTask().getId() + "-" + taskTo_2.getTask().getId();
            }
        }
        return state;
    }

    public ModellingProcessor getConnectedToProcessor() {
        return connectedToProcessor;
    }

    public boolean alreadySendThisData(Direction direction, ModellingTask fromTask, ModellingTask toTask, ModellingProcessor sendToProcessor) {
//        if (taskFrom_1 == null | taskFrom_2 == null) {
        if (taskFrom_1 == null) {
            return false;
        }
        return ((direction1 == direction & taskFrom_1.equals(fromTask) & taskTo_1.equals(toTask) & (connectedToProcessor != null && connectedToProcessor.equals(sendToProcessor))));
//        return ((direction1 == direction & taskFrom_1.equals(fromTask) & taskTo_1.equals(toTask) & connectedToProcessor.equals(sendToProcessor))) ||
//                ((direction2 == direction & taskFrom_2.equals(fromTask) & taskTo_2.equals(toTask) & connectedToProcessor.equals(sendToProcessor)));
    }

    public ModellingTask getTaskTo_1() {
        return taskTo_1;
    }

    public ModellingTask getTaskTo_2() {
        return taskTo_2;
    }

    public enum State {
        BUSY, FREE
    }

    public enum Direction {
        SEND, RECEIVE, READY
    }

    @Override
    public String toString() {
        return "ModellingConnection{" +
                "state1=" + state1 +
                '}';
    }
}
