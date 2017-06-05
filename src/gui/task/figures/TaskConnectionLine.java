package gui.task.figures;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskConnectionLine {
    private TaskOval taskFromOval;
    private TaskOval taskToOval;

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private String name;
    private int nameX;
    private int nameY;

    private double bandwidth = -1.0;

    public TaskConnectionLine(TaskOval taskFromOval, TaskOval taskToOval, int taskOvalRadius, double bandwidth) {
        this.taskFromOval = taskFromOval;
        this.taskToOval = taskToOval;

        this.x1 = taskFromOval.getX() + taskOvalRadius / 2;
        this.y1 = taskFromOval.getY() + taskOvalRadius / 2;
        this.x2 = taskToOval.getX() + taskOvalRadius / 2;
        this.y2 = taskToOval.getY() + taskOvalRadius / 2;

//        this.name = "e" + taskFromOval.getId() + taskToOval.getId();
        this.bandwidth = bandwidth;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public TaskOval getTaskFromOval() {
        return taskFromOval;
    }

    public TaskOval getTaskToOval() {
        return taskToOval;
    }

    public String getName() {
        return "e" + bandwidth;
    }

    public int getNameX() {
        nameX = (x1 + x2) / 2;
        return nameX;
    }

    public int getNameY() {
        nameY = (y1 + y2) / 2;
        return nameY;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }
}
