package gui.system.figures;

import java.awt.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class ProcessorSquare {
    private int id;
    private double power;
    private int x;
    private int y;
    private int radius;
    private Color fillColor;
    private Color color;

    public ProcessorSquare(int id, double power, int x, int y, int radius, Color color, Color fillColor) {
        this.id = id;
        this.power = power;
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

    public void setPower(double power) {
        this.power = power;
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


    public double getPower() {
        return power;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
}
