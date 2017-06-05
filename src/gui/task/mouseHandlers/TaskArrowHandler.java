package gui.task.mouseHandlers;

import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import utils.WorkManager;

import javax.swing.*;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskArrowHandler {
    private WorkManager workManager;
    private TaskGraphPanel taskGraphPanel;
    private EditTaskGraphPanel editTaskGraphPanel;
    private int vertexRadius = 0;
    private boolean mousePressed = false;
    private int currentEntityId = -1;
    private int newX = 0;
    private int newY = 0;

    public TaskArrowHandler(WorkManager workManager, EditTaskGraphPanel editTaskGraphPanel, TaskGraphPanel taskGraphPanel) {
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
                    newX = taskGraphPanel.getTaskOval(currentEntityId).getX();
                    newY = taskGraphPanel.getTaskOval(currentEntityId).getY();
                }
                break;
            case RELEASED:
                mousePressed = false;
                if (currentEntityId != -1) {
                    //set new xy
                    workManager.editTask(currentEntityId, newX, newY);
                }
                break;
            case MOVE:
                if (mousePressed & currentEntityId != -1) {
                    //move vertex
                    newX = taskMouseEvent.getX() - vertexRadius / 2;
                    newY = taskMouseEvent.getY() - vertexRadius / 2;
                    if (newX < 0) newX = 0;
                    if (newX > taskGraphPanel.getWidth() - vertexRadius)
                        newX = taskGraphPanel.getWidth() - vertexRadius - 1;
                    if (newY < 0) newY = 0;
                    if (newY > taskGraphPanel.getHeight() - vertexRadius)
                        newY = taskGraphPanel.getHeight() - vertexRadius - 1;
                    taskGraphPanel.setNewCoordinate(currentEntityId, newX, newY);
                    editTaskGraphPanel.repaint();
                }
                break;
            case ENTERED:

                break;
            case EXITED:
                mousePressed = false;
                break;
            case DOUBLE_CLICK:
                mousePressed = false;
                this.currentEntityId = TaskHandlerUtils.defineVertexId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                if (currentEntityId != -1) {
                    String newWeightStr = JOptionPane.showInputDialog("Поточна вага: " + taskGraphPanel.getTaskOval(currentEntityId).getWeight() + " . Введіть нову вагу");
                    try {
                        double newWeight = Double.parseDouble(newWeightStr);
                        workManager.setNewWeight(currentEntityId, newWeight);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Не вірне значення! Вага не змінена.");
                    } catch (NullPointerException e) {
                    }
                } else {
                    int[] connectionOvalIds = TaskHandlerUtils.defineConnectionId(taskMouseEvent.getX(), taskMouseEvent.getY(), taskGraphPanel);
                    if (connectionOvalIds[0] != -1) {
                        String newBandwidthStr = JOptionPane.showInputDialog("Поточна пропускна здатність: " + taskGraphPanel.getTaskConnectionBandwidth(connectionOvalIds[0], connectionOvalIds[1]) + " . Введіть нову вагу");
                        try {
                            double newBandwidth = Double.parseDouble(newBandwidthStr);
                            workManager.setNewBandwidth(connectionOvalIds[0], connectionOvalIds[1], newBandwidth);
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
