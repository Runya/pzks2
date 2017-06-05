package gui.task.listeners;

import gui.task.mouseHandlers.TaskHandlerManager;
import gui.task.mouseHandlers.TaskMouseEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskGraphMouseListener implements MouseListener {
    private TaskHandlerManager taskHandlerManager;

    public TaskGraphMouseListener(TaskHandlerManager taskHandlerManager) {
        this.taskHandlerManager = taskHandlerManager;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            taskHandlerManager.handleMouseEvent(new TaskMouseEvent(TaskMouseEvent.MouseEventType.DOUBLE_CLICK,
                    mouseEvent.getX(), mouseEvent.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
//        System.out.println("Press (" + mouseEvent.getX() + "," + mouseEvent.getY() + ")");
        taskHandlerManager.handleMouseEvent(new TaskMouseEvent(TaskMouseEvent.MouseEventType.PRESSED,
                mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        taskHandlerManager.handleMouseEvent(new TaskMouseEvent(TaskMouseEvent.MouseEventType.RELEASED,
                mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
//        System.out.println("Entered...");
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
//        System.out.println("Exited...");
    }
}
