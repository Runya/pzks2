package entities.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hadgehog on 16.02.14.
 */
public class Task implements Serializable {
    public static final int DEFAULT_WEIGHT = 2;

    private int id;
    private double weight;
    private int x;
    private int y;

    private Set<Integer> dependsFrom = new HashSet<>();
    private ArrayList<Integer> fatherFor = new ArrayList<>();

    public Task(int id, double weight, int x, int y) {
        this(id, x, y);
        this.weight = weight;
    }

    public Task(int id, double weight) {
        this(id, weight,0, 0);
    }

    public Task(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.weight = DEFAULT_WEIGHT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
        dependsFrom.add(taskId);
    }

    public void removeDependsFrom(int taskId) {
        dependsFrom.remove(Integer.valueOf(taskId));
    }

    public Set<Integer> getDependsFrom() {
        return dependsFrom;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", weight=" + weight +
                ", x=" + x +
                ", y=" + y +
                ", dependsFrom=" + dependsFrom +
                ", fatherFor=" + fatherFor +
                '}';
    }
}
