package modelling.entities;

import entities.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 10.05.14.
 */
public class ModellingTask {
    private final Task task;
    private List<ModellingTask> dependsFrom;
    private List<ModellingTask> dependsFromOriginal;
    private State state;
    private int timeToRun;
    public int onRecive = -1;
//    public boolean recived;


    public ModellingTask(Task task, State state) {
        this.task = task;
        this.state = state;
    }

    public void setDependsFrom(List<ModellingTask> dependsFrom) {
        this.dependsFrom = dependsFrom;
        dependsFromOriginal = new ArrayList<>();
        for (ModellingTask modellingTask : dependsFrom) {
            dependsFromOriginal.add(modellingTask);
        }
    }

    public Task getTask() {
        return task;
    }

    public State getState() {
        return state;
    }

    public ModellingTask setStateAndTime(State state, int timeToRun) {
        this.state = state;
        this.timeToRun = timeToRun;
        return this;
    }

    public boolean executeStep() {
        if (--timeToRun == 0) {
            state = State.COMPLETED;
            return true;
        }
        return false;
    }

    public List<ModellingTask> getDependsFrom() {
        return dependsFromOriginal;
    }

    @Override
    public String toString() {
        return "ModellingTask{" +
                "task=" + task +
                ", state=" + state +
                ", timeToRun=" + timeToRun +
                '}';
    }

    public void taskCompleted(ModellingTask task) {
        dependsFrom.remove(task);
        if (state == State.NOT_READY & dependsFrom.size() == 0) {
            state = State.READY;
        }
    }

    public enum State {
        NOT_READY, READY, IN_PROGRESS, COMPLETED
    }
}
