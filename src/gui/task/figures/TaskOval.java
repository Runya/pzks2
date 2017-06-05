package gui.task.figures;

import java.awt.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskOval {
    private int id;
    private double weight;
    private int x;
    private int y;
    private int radius;
    private Color fillColor;
    private Color color;

    public TaskOval(int id, double weight, int x, int y, int radius, Color color, Color fillColor) {
        this.id = id;
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.color = color;
        this.fillColor = fillColor;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getFillColor() {
        return fillColor;
    }


    public double getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
}
