package entities.task;

/**
 * Created by hadgehog on 30.03.2014.
 */
public class TaskQueueData {
    private int id;
    private int connectivity;
    private int criticalVertexWay;
    private double deltaTime;

    public TaskQueueData(int id) {
        this.id = id;
    }

    public TaskQueueData(int id, double deltaTime) {
        this.id = id;
        this.deltaTime = deltaTime;
    }

    public TaskQueueData(int id, int criticalWay) {
        this.id = id;
        this.criticalVertexWay = criticalWay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(int connectivity) {
        this.connectivity = connectivity;
    }

    public int getCriticalVertexWay() {
        return criticalVertexWay;
    }

    public void setCriticalVertexWay(int criticalVertexWay) {
        this.criticalVertexWay = criticalVertexWay;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }
}
