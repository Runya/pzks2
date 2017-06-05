package gui.system.mouseHandlers;

import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import gui.task.mouseHandlers.TaskMouseEvent;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemNewVertexHandler {
    private WorkManager workManager;
    private SystemGraphPanel systemGraphPanel;
    private EditSystemGraphPanel editSystemGraphPanel;
    private int vertexRadius = 0;
    private int newX = 0;
    private int newY = 0;

    public SystemNewVertexHandler(WorkManager workManager, EditSystemGraphPanel editSystemGraphPanel, SystemGraphPanel systemGraphPanel) {
        this.workManager = workManager;
        this.systemGraphPanel = systemGraphPanel;
        this.editSystemGraphPanel = editSystemGraphPanel;
        this.vertexRadius = systemGraphPanel.getProcessorSquareRadius();
    }

    public void handleMouseEvent(SystemMouseEvent systemMouseEvent) {
        switch (systemMouseEvent.getType()) {

            case PRESSED:
                break;
            case RELEASED:
                newX = systemMouseEvent.getX();
                newY = systemMouseEvent.getY();
                if (newX < 0) newX = 0;
                if (newX > systemGraphPanel.getWidth() - vertexRadius)
                    newX = systemGraphPanel.getWidth() - vertexRadius - 1;
                if (newY < 0) newY = 0;
                if (newY > systemGraphPanel.getHeight() - vertexRadius)
                    newY = systemGraphPanel.getHeight() - vertexRadius - 1;
                workManager.addProcessor(newX, newY);
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
