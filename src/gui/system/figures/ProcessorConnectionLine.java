package gui.system.figures;

/**
 * Created by hadgehog on 16.02.14.
 */
public class ProcessorConnectionLine {
    private ProcessorSquare processorFromSquare;
    private ProcessorSquare processorToSquare;

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private String name;
    private int nameX;
    private int nameY;

    private double bandwidth = -1.0;

    public ProcessorConnectionLine(ProcessorSquare processorFromSquare, ProcessorSquare processorToSquare, int processorSquareRadius, double bandwidth) {
        this.processorFromSquare = processorFromSquare;
        this.processorToSquare = processorToSquare;

        this.x1 = processorFromSquare.getX() + processorSquareRadius / 2;
        this.y1 = processorFromSquare.getY() + processorSquareRadius / 2;
        this.x2 = processorToSquare.getX() + processorSquareRadius / 2;
        this.y2 = processorToSquare.getY() + processorSquareRadius / 2;

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

    public ProcessorSquare getTaskFromOval() {
        return processorFromSquare;
    }

    public ProcessorSquare getTaskToOval() {
        return processorToSquare;
    }

    public String getName() {
        return "u" + bandwidth;
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
