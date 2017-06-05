package utils.comparators;

import entities.task.TaskQueueData;

import java.util.Comparator;

/**
 * Created by hadgehog on 30.03.2014.
 */
public class TaskQueue1Comparator implements Comparator<TaskQueueData> {

    @Override
    public int compare(TaskQueueData taskQueueData, TaskQueueData taskQueueData2) {
        return taskQueueData.getDeltaTime() > taskQueueData2.getDeltaTime() ? 1 : -1;
    }
}
