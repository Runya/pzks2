package gui.system.mouseHandlers;

import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.system.figures.ProcessorSquare;
import utils.WorkManager;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemNewConnectionHandler {
    private WorkManager workManager;
    private SystemGraphPanel systemGraphPanel;
    private EditSystemGraphPanel editSystemGraphPanel;
    private int vertexRadius = 0;
    private boolean mousePressed = false;
    private int currentEntityId = -1;
    private int newX = 0;
    private int newY = 0;

    public SystemNewConnectionHandler(WorkManager workManager, EditSystemGraphPanel editSystemGraphPanel, SystemGraphPanel systemGraphPanel) {
        this.workManager = workManager;
        this.systemGraphPanel = systemGraphPanel;
        this.editSystemGraphPanel = editSystemGraphPanel;
        this.vertexRadius = systemGraphPanel.getProcessorSquareRadius();
    }

    public void handleMouseEvent(SystemMouseEvent systemMouseEvent) {
        switch (systemMouseEvent.getType()) {

            case PRESSED:
                mousePressed = true;
                //define id
                this.currentEntityId = SystemHandlerUtils.defineVertexId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                if (currentEntityId != -1) {
                    systemGraphPanel.drawTempConnectionLine = true;
                    ProcessorSquare processorSquare = systemGraphPanel.getProcessorSquare(currentEntityId);
                    systemGraphPanel.tempLineX1 = processorSquare.getX() + vertexRadius / 2;
                    systemGraphPanel.tempLineY1 = processorSquare.getY() + vertexRadius / 2;
                    systemGraphPanel.tempLineX2 = systemMouseEvent.getX();
                    systemGraphPanel.tempLineY2 = systemMouseEvent.getY();
                }
                break;
            case RELEASED:
                mousePressed = false;
                systemGraphPanel.drawTempConnectionLine = false;
                int toProcessorId = SystemHandlerUtils.defineVertexId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                if (currentEntityId != -1 & toProcessorId != -1) {
                    if (currentEntityId != toProcessorId) {
                        workManager.addSystemConnection(toProcessorId, currentEntityId);
                    }
                }
                editSystemGraphPanel.repaint();
                break;
            case MOVE:
                if (mousePressed & currentEntityId != -1) {
                    systemGraphPanel.tempLineX2 = systemMouseEvent.getX();
                    systemGraphPanel.tempLineY2 = systemMouseEvent.getY();
                    editSystemGraphPanel.repaint();
                }
                break;
            case ENTERED:
                break;
            case EXITED:
                mousePressed = false;
                systemGraphPanel.drawTempConnectionLine = false;
                editSystemGraphPanel.repaint();
                break;
            case DOUBLE_CLICK:
                break;
        }
    }
}
