package gui.task.mouseHandlers;

import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import gui.task.figures.TaskOval;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskNewConnectionHandler {
    private WorkManager workManager;
    private TaskGraphPanel taskGraphPanel;
    private EditTaskGraphPanel editTaskGraphPanel;
    private int vertexRadius = 0;
    private boolean mousePressed = false;
    private int currentEntityId = -1;
    private int newX = 0;
    private int newY = 0;

    public TaskNewConnectionHandler(WorkManager workManager, EditTaskGraphPanel editTaskGraphPanel, TaskGraphPanel taskGraphPanel) {
        this.workManager = workManager;
        this.taskGraphPanel = taskGraphPanel;
        this.editTaskGraphPanel = editTaskGraphPanel;
        this.vertexRadius = taskGraphPanel.getTaskOvalRadius();
    }

    public void handleMouseEvent(TaskMouseEvent taskMouseEvent) {
        switch (taskMouseEvent.getType()) {

            case PRESSED:
                mousePressed = true;
                //define id
                this.currentEntityId = TaskHandlerUtils.defineVertexId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                if (currentEntityId != -1) {
                    taskGraphPanel.drawTempConnectionLine = true;
                    TaskOval taskOval = taskGraphPanel.getTaskOval(currentEntityId);
                    taskGraphPanel.tempLineX1 = taskOval.getX() + vertexRadius / 2;
                    taskGraphPanel.tempLineY1 = taskOval.getY() + vertexRadius / 2;
                    taskGraphPanel.tempLineX2 = taskMouseEvent.getX();
                    taskGraphPanel.tempLineY2 = taskMouseEvent.getY();
                }
                break;
            case RELEASED:
                mousePressed = false;
                taskGraphPanel.drawTempConnectionLine = false;
                int toTaskId = TaskHandlerUtils.defineVertexId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                if (currentEntityId != -1 & toTaskId != -1) {
                    if (currentEntityId != toTaskId) {
                        workManager.addConnection(toTaskId, currentEntityId);
                    }
                }
                editTaskGraphPanel.repaint();
                break;
            case MOVE:
                if (mousePressed & currentEntityId != -1) {
                    taskGraphPanel.tempLineX2 = taskMouseEvent.getX();
                    taskGraphPanel.tempLineY2 = taskMouseEvent.getY();
                    editTaskGraphPanel.repaint();
                }
                break;
            case ENTERED:
                break;
            case EXITED:
                mousePressed = false;
                taskGraphPanel.drawTempConnectionLine = false;
                editTaskGraphPanel.repaint();
                break;
            case DOUBLE_CLICK:
                break;
        }
    }
}
