package utils;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;
import modelling.TaskQueueUtils;
import modelling.entities.ResultModelling;
import modelling.entities.ResultModelling2;
import modelling.entities.ResultModelling3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadgehog on 30.03.2014.
 */
public class ModellingManager {
    public static void createQueue(TaskGraph taskGraph) {
        List<int[]> queues = new ArrayList<>();
        for (TaskQueueUtils.TaskQueueType taskQueueType : TaskQueueUtils.TaskQueueType.values()) {
            TaskQueueUtils.getQueue(taskGraph, taskQueueType);
        }
    }

    public static ResultModelling createResultModelling1(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue) {
        ResultModelling resultModelling = new ResultModelling2(taskGraph, computerSystem, queue);
        resultModelling.model();
        return resultModelling;
    }

    public static ResultModelling createResultModelling2(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue) {
        ResultModelling resultModelling = new ResultModelling3(taskGraph, computerSystem, queue);
        resultModelling.model();
        return resultModelling;
    }

}
