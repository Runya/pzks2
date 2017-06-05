package entities.task;

import java.io.Serializable;

/**
 * Created by hadgehog on 20.02.14.
 */
public class TaskConnection implements Serializable {
    private double DEFAULT_BANDWIDTH = 1;
    private double bandwidth = 0;
    private int fromTaskId;
    private int toTaskId;

    public TaskConnection(int fromTaskId, int toTaskId, double bandwidth) {
        this(fromTaskId, toTaskId);
        this.bandwidth = bandwidth;
    }

    public TaskConnection(int fromTaskId, int toTaskId) {
        this.bandwidth = DEFAULT_BANDWIDTH;
        this.fromTaskId = fromTaskId;
        this.toTaskId = toTaskId;
    }

    public void setDEFAULT_BANDWIDTH(double DEFAULT_BANDWIDTH) {
        this.DEFAULT_BANDWIDTH = DEFAULT_BANDWIDTH;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public int getFromTaskId() {
        return fromTaskId;
    }

    public int getToTaskId() {
        return toTaskId;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }
    public void increaseBandwidth(double bandwidth) {
        this.bandwidth += bandwidth;
    }


}
