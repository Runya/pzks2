package gui.system.listeners;

import gui.system.mouseHandlers.SystemHandlerManager;
import gui.system.mouseHandlers.SystemMouseEvent;
import gui.task.mouseHandlers.TaskHandlerManager;
import gui.task.mouseHandlers.TaskMouseEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class SystemGraphMouseMotionListener implements MouseMotionListener {
    private SystemHandlerManager systemHandlerManager;

    public SystemGraphMouseMotionListener(SystemHandlerManager systemHandlerManager) {
        this.systemHandlerManager = systemHandlerManager;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        systemHandlerManager.handleMouseEvent(new SystemMouseEvent(SystemMouseEvent.MouseEventType.MOVE,
                mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
