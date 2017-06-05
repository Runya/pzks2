package tests;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;
import org.junit.Test;
import utils.WorkManager;

/**
 * Created by hadgehog on 16.02.14.
 */

public class TestGui {
    @Test
    public void createTasksPanel() {
        TaskGraph taskGraph = new TaskGraph();
        taskGraph.addTask(100, 200);

        ComputerSystem computerSystem = null;
        WorkManager workManager = new WorkManager(taskGraph, computerSystem);

//        assert workManager
    }
}
