package gui.task.mouseHandlers;

import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskRemoveHandler {
    private WorkManager workManager;
    private TaskGraphPanel taskGraphPanel;
    private EditTaskGraphPanel editTaskGraphPanel;
    private int vertexRadius = 0;
    private int currentEntityId = -1;

    public TaskRemoveHandler(WorkManager workManager, EditTaskGraphPanel editTaskGraphPanel, TaskGraphPanel taskGraphPanel) {
        this.workManager = workManager;
        this.taskGraphPanel = taskGraphPanel;
        this.editTaskGraphPanel = editTaskGraphPanel;
        this.vertexRadius = taskGraphPanel.getTaskOvalRadius();
    }

    public void handleMouseEvent(TaskMouseEvent taskMouseEvent) {
        switch (taskMouseEvent.getType()) {

            case PRESSED:
                //remove connections
                this.currentEntityId = TaskHandlerUtils.defineVertexId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                if (currentEntityId != -1) {
                    workManager.removeTask(currentEntityId);
                } else {
                    int[] connectionOvalIds = TaskHandlerUtils.defineConnectionId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                    if (connectionOvalIds[0] != -1) {
                        workManager.removeConnection(connectionOvalIds[1], connectionOvalIds[0]);
                    }
                }
                break;
            case RELEASED:
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
