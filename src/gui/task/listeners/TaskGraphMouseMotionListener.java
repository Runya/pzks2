package gui.task.listeners;

import gui.task.mouseHandlers.TaskHandlerManager;
import gui.task.mouseHandlers.TaskMouseEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskGraphMouseMotionListener implements MouseMotionListener {
    private TaskHandlerManager taskHandlerManager;

    public TaskGraphMouseMotionListener(TaskHandlerManager taskHandlerManager) {
        this.taskHandlerManager = taskHandlerManager;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
//        System.out.println("Mouse moved...");
        taskHandlerManager.handleMouseEvent(new TaskMouseEvent(TaskMouseEvent.MouseEventType.MOVE,
                mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
