package gui.system.mouseHandlers;

import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import gui.task.mouseHandlers.TaskHandlerUtils;
import gui.task.mouseHandlers.TaskMouseEvent;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemRemoveHandler {
    private WorkManager workManager;
    private SystemGraphPanel systemGraphPanel;
    private EditSystemGraphPanel editSystemGraphPanel;
    private int vertexRadius = 0;
    private int currentEntityId = -1;

    public SystemRemoveHandler(WorkManager workManager, EditSystemGraphPanel editSystemGraphPanel, SystemGraphPanel taskGraphPanel) {
        this.workManager = workManager;
        this.systemGraphPanel = taskGraphPanel;
        this.editSystemGraphPanel = editSystemGraphPanel;
        this.vertexRadius = taskGraphPanel.getProcessorSquareRadius();
    }

    public void handleMouseEvent(SystemMouseEvent systemMouseEvent) {
        switch (systemMouseEvent.getType()) {

            case PRESSED:
                //remove connections
                this.currentEntityId = SystemHandlerUtils.defineVertexId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                if (currentEntityId != -1) {
                    workManager.removeProcessor(currentEntityId);
                } else {
                    int[] connectionOvalIds = SystemHandlerUtils.defineConnectionId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                    if (connectionOvalIds[0] != -1) {
                        workManager.removeSystemConnection(connectionOvalIds[1], connectionOvalIds[0]);
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
