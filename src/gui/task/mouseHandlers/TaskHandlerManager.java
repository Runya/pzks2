package gui.task.mouseHandlers;

import gui.task.EditTaskGraphPanel;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskHandlerManager {
    private WorkManager workManager;
    private EditTaskGraphPanel editTaskGraphPanel;
    private TaskArrowHandler taskArrowHandler;
    private TaskRemoveHandler taskRemoveHandler;
    private TaskNewVertexHandler taskNewVertexHandler;
    private TaskNewConnectionHandler taskNewConnectionHandler;

    public void setData(WorkManager workManager) {
        this.workManager = workManager;
        this.editTaskGraphPanel = workManager.getEditTaskGraphPanel();
        this.taskArrowHandler = new TaskArrowHandler(workManager, workManager.getEditTaskGraphPanel(), workManager.getTaskGraphPanel());
        this.taskRemoveHandler = new TaskRemoveHandler(workManager, workManager.getEditTaskGraphPanel(), workManager.getTaskGraphPanel());
        this.taskNewVertexHandler = new TaskNewVertexHandler(workManager, workManager.getEditTaskGraphPanel(), workManager.getTaskGraphPanel());
        this.taskNewConnectionHandler = new TaskNewConnectionHandler(workManager, workManager.getEditTaskGraphPanel(), workManager.getTaskGraphPanel());
    }

    public void handleMouseEvent(TaskMouseEvent taskMouseEvent) {
        //визначаємо тип інструменту
        switch (editTaskGraphPanel.getActiveTaskToolType()) {
            case MOUSE:
                taskArrowHandler.handleMouseEvent(taskMouseEvent);
                break;
            case NEW_VERTEX:
                taskNewVertexHandler.handleMouseEvent(taskMouseEvent);
                break;
            case CONNECTION:
                taskNewConnectionHandler.handleMouseEvent(taskMouseEvent);
                break;
            case REMOVE:
                taskRemoveHandler.handleMouseEvent(taskMouseEvent);
                break;
        }
        ;
    }
}
