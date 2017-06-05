package gui.system.mouseHandlers;

import gui.system.EditSystemGraphPanel;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemHandlerManager {
    private WorkManager workManager;
    private EditSystemGraphPanel editSystemGraphPanel;
    private SystemArrowHandler systemArrowHandler;
    private SystemNewVertexHandler systemNewVertexHandler;
    private SystemRemoveHandler systemRemoveHandler;
    private SystemNewConnectionHandler systemNewConnectionHandler;

    public void setData(WorkManager workManager) {
        this.workManager = workManager;
        this.editSystemGraphPanel = workManager.getEditSystemTaskGraphPanel();
        this.systemArrowHandler = new SystemArrowHandler(workManager, workManager.getEditSystemTaskGraphPanel(), workManager.getSystemGraphPanel());
        this.systemNewVertexHandler = new SystemNewVertexHandler(workManager, workManager.getEditSystemTaskGraphPanel(), workManager.getSystemGraphPanel());
        this.systemRemoveHandler = new SystemRemoveHandler(workManager, workManager.getEditSystemTaskGraphPanel(), workManager.getSystemGraphPanel());
        this.systemNewConnectionHandler = new SystemNewConnectionHandler(workManager, workManager.getEditSystemTaskGraphPanel(), workManager.getSystemGraphPanel());
    }

    public void handleMouseEvent(SystemMouseEvent systemMouseEvent) {
        //визначаємо тип інструменту
        switch (editSystemGraphPanel.getActiveSystemToolType()) {
            case MOUSE:
                systemArrowHandler.handleMouseEvent(systemMouseEvent);
                break;
            case NEW_VERTEX:
                systemNewVertexHandler.handleMouseEvent(systemMouseEvent);
                break;
            case CONNECTION:
                systemNewConnectionHandler.handleMouseEvent(systemMouseEvent);
                break;
            case REMOVE:
                systemRemoveHandler.handleMouseEvent(systemMouseEvent);
                break;
        }
    }
}
