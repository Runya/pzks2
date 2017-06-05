package utils.comparators;

import entities.task.TaskQueueData;

import java.util.Comparator;

/**
 * Created by hadgehog on 30.03.2014.
 */
public class TaskQueue2Comparator implements Comparator<TaskQueueData> {

    @Override
    public int compare(TaskQueueData taskQueueData, TaskQueueData taskQueueData2) {
        if (taskQueueData.getConnectivity() == taskQueueData2.getConnectivity()) {
            return taskQueueData2.getCriticalVertexWay() - taskQueueData.getCriticalVertexWay();
        } else {
            return taskQueueData2.getConnectivity() - taskQueueData.getConnectivity();
        }
    }
}
