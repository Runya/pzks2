package entities.computerSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hadgehog on 20.02.14.
 */
public class Processor implements Serializable {
    public static double DEFAULT_POWER = 1;
    public static boolean IN_OUT_PROCESSOR = true;

    private int id;
    private double power;
    private int x;
    private int y;

    private Set<Integer> dependsFrom = new HashSet<>();
    private ArrayList<Integer> fatherFor = new ArrayList<>();
    private int connectivity;

    public Processor(int id, int power, int x, int y) {
        this(id, x, y);
        this.power = power;
    }

    public Processor(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.power = DEFAULT_POWER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addDependsFrom(int taskId) {
        dependsFrom.add(Integer.valueOf(taskId));
    }

    public void removeDependsFrom(int taskId) {
        dependsFrom.remove(Integer.valueOf(taskId));
    }

    public Set<Integer> getDependsFrom() {
        return dependsFrom;
    }

    @Override
    public String toString() {
        return "Processor{" +
                "id=" + id +
                ", power=" + power +
                '}';
    }
}
