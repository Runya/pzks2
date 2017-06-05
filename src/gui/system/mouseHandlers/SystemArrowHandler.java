package gui.system.mouseHandlers;

import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import gui.task.mouseHandlers.TaskHandlerUtils;
import gui.task.mouseHandlers.TaskMouseEvent;
import utils.WorkManager;

import javax.swing.*;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemArrowHandler {
    private WorkManager workManager;
    private SystemGraphPanel systemGraphPanel;
    private EditSystemGraphPanel editSystemGraphPanel;
    private int vertexRadius = 0;
    private boolean mousePressed = false;
    private int currentEntityId = -1;
    private int newX = 0;
    private int newY = 0;

    public SystemArrowHandler(WorkManager workManager, EditSystemGraphPanel editSystemGraphPanel, SystemGraphPanel systemGraphPanel) {
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
                    newX = systemGraphPanel.getProcessorSquare(currentEntityId).getX();
                    newY = systemGraphPanel.getProcessorSquare(currentEntityId).getY();
                }
                break;
            case RELEASED:
                mousePressed = false;
                if (currentEntityId != -1) {
                    //set new xy
                    workManager.editProcessor(currentEntityId, newX, newY);
                }
                break;
            case MOVE:
                if (mousePressed & currentEntityId != -1) {
                    //move vertex
                    newX = systemMouseEvent.getX() - vertexRadius / 2;
                    newY = systemMouseEvent.getY() - vertexRadius / 2;
                    if (newX < 0) newX = 0;
                    if (newX > systemGraphPanel.getWidth() - vertexRadius)
                        newX = systemGraphPanel.getWidth() - vertexRadius - 1;
                    if (newY < 0) newY = 0;
                    if (newY > systemGraphPanel.getHeight() - vertexRadius)
                        newY = systemGraphPanel.getHeight() - vertexRadius - 1;
                    systemGraphPanel.setNewCoordinate(currentEntityId, newX, newY);
                    editSystemGraphPanel.repaint();
                }
                break;
            case ENTERED:

                break;
            case EXITED:
                mousePressed = false;
                break;
            case DOUBLE_CLICK:
                mousePressed = false;
                this.currentEntityId = SystemHandlerUtils.defineVertexId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                if (currentEntityId != -1) {
                    String newWeightStr = JOptionPane.showInputDialog("Поточна продуктивність: " + systemGraphPanel.getProcessorSquare(currentEntityId).getPower() + " . Введіть нову продуктивнісь");
                    try {
                        double newWeight = Double.parseDouble(newWeightStr);
                        workManager.setNewPower(currentEntityId, newWeight);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Не вірне значення! Продуктивність не змінена.");
                    } catch (NullPointerException e) {
                    }
                } else {
                    int[] connectionOvalIds = SystemHandlerUtils.defineConnectionId(systemMouseEvent.getX(), systemMouseEvent.getY(), systemGraphPanel);
                    if (connectionOvalIds[0] != -1) {
                        String newBandwidthStr = JOptionPane.showInputDialog("Поточна пропускна здатність: " + systemGraphPanel.getProcessorConnectionBandwidth(connectionOvalIds[0], connectionOvalIds[1]) + " . Введіть нову вагу");
                        try {
                            double newBandwidth = Double.parseDouble(newBandwidthStr);
                            workManager.setNewSystemConnectionBandwidth(connectionOvalIds[0], connectionOvalIds[1], newBandwidth);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Не вірне значення! пропускна здатність не змінена.");
                        } catch (NullPointerException e) {
                        }
                    }
                }
                break;
        }
    }
}
