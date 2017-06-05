package gui.task.mouseHandlers;

import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskNewVertexHandler {
    private WorkManager workManager;
    private TaskGraphPanel taskGraphPanel;
    private EditTaskGraphPanel editTaskGraphPanel;
    private int vertexRadius = 0;
    private int newX = 0;
    private int newY = 0;

    public TaskNewVertexHandler(WorkManager workManager, EditTaskGraphPanel editTaskGraphPanel, TaskGraphPanel taskGraphPanel) {
        this.workManager = workManager;
        this.taskGraphPanel = taskGraphPanel;
        this.editTaskGraphPanel = editTaskGraphPanel;
        this.vertexRadius = taskGraphPanel.getTaskOvalRadius();
    }

    public void handleMouseEvent(TaskMouseEvent taskMouseEvent) {
        switch (taskMouseEvent.getType()) {

            case PRESSED:
                break;
            case RELEASED:
                newX = taskMouseEvent.getX();
                newY = taskMouseEvent.getY();
                if (newX < 0) newX = 0;
                if (newX > taskGraphPanel.getWidth() - vertexRadius)
                    newX = taskGraphPanel.getWidth() - vertexRadius - 1;
                if (newY < 0) newY = 0;
                if (newY > taskGraphPanel.getHeight() - vertexRadius)
                    newY = taskGraphPanel.getHeight() - vertexRadius - 1;
                workManager.addTask(newX, newY);
                break;
            case MOVE:
                break;
            case ENTERED:
                break;
            case EXITED:
                break;
            case DOUBLE_CLICK:
                break;
        }
    }

}
