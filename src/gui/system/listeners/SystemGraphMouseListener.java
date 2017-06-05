package gui.system.listeners;

import gui.system.mouseHandlers.SystemHandlerManager;
import gui.system.mouseHandlers.SystemMouseEvent;
import gui.task.mouseHandlers.TaskHandlerManager;
import gui.task.mouseHandlers.TaskMouseEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class SystemGraphMouseListener implements MouseListener {
    private SystemHandlerManager systemHandlerManager;

    public SystemGraphMouseListener(SystemHandlerManager systemHandlerManager) {
        this.systemHandlerManager = systemHandlerManager;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            systemHandlerManager.handleMouseEvent(new SystemMouseEvent(SystemMouseEvent.MouseEventType.DOUBLE_CLICK,
                    mouseEvent.getX(), mouseEvent.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
//        System.out.println("Press (" + mouseEvent.getX() + "," + mouseEvent.getY() + ")");
        systemHandlerManager.handleMouseEvent(new SystemMouseEvent(SystemMouseEvent.MouseEventType.PRESSED,
                mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        systemHandlerManager.handleMouseEvent(new SystemMouseEvent(SystemMouseEvent.MouseEventType.RELEASED,
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
