package gui;

import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class MainFrame extends JFrame {
    private WorkManager workManager;

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private EditTaskGraphPanel editTaskGraphPanel;
    private EditSystemGraphPanel editSystemGraphPanel;

    public MainFrame(WorkManager workManager) {
        this.workManager = workManager;

        setSize_Position();
        setLayoutParameters();
        setJMenuBar(new MenuBar(workManager));
        addEditPanels();

        drawTasks();
        drawSystem();

        setBackground(Color.getHSBColor(0.0f, 1.42f, 55.29f));
        setVisible(true);

    }

    public void drawTasks() {
        editTaskGraphPanel.getTaskGraphPanel().removeAllTasks();
        int[][] tasksData = workManager.getTasksData(); // [id, x, y]
        for (int[] taskData : tasksData) {
            double weight = workManager.getTaskWeight(taskData[0]);
            editTaskGraphPanel.getTaskGraphPanel().addTaskToDraw(taskData[0], weight, taskData[1], taskData[2]);
        }
        editTaskGraphPanel.repaint();
        drawConnections();
    }

    public void drawSystem() {
        editSystemGraphPanel.getSystemGraphPanel().removeAllProcessors();
        int[][] processorsData = workManager.getProcessorsData(); // [id, x, y]
        for (int[] processorData : processorsData) {
            double power = workManager.getProcessorPower(processorData[0]);
            editSystemGraphPanel.getSystemGraphPanel().addProcessorToDraw(processorData[0], power, processorData[1], processorData[2]);
        }
        editSystemGraphPanel.repaint();
        drawProcessorConnections();
    }

    public void drawConnections() {
        editTaskGraphPanel.getTaskGraphPanel().removeAllConnections();
        int[][] connectionsData = workManager.getConnectionsData(); //  [fromTaskId, toTaskId]
        for (int[] connectionData : connectionsData) {
            double bandwidth = workManager.getTaskConnectionBandwidth(connectionData[0], connectionData[1]);
            editTaskGraphPanel.getTaskGraphPanel().addConnectionToDraw(connectionData[0], connectionData[1], bandwidth);
        }
        editTaskGraphPanel.repaint();
    }

    public void drawProcessorConnections() {
        editSystemGraphPanel.getSystemGraphPanel().removeAllConnections();
        int[][] connectionsData = workManager.getSystemConnectionsData(); //  [fromTaskId, toTaskId]
        for (int[] connectionData : connectionsData) {
            double power = workManager.getSystemConnectionBandwidth(connectionData[0], connectionData[1]);
            editSystemGraphPanel.getSystemGraphPanel().addConnectionToDraw(connectionData[0], connectionData[1], power);
        }
        editTaskGraphPanel.repaint();
    }

    public void addTaskToDraw(int[] taskData, double weight) {
        editTaskGraphPanel.getTaskGraphPanel().addTaskToDraw(taskData[0], weight, taskData[1], taskData[2]);
        drawConnections();
    }

    public void addProcessorToDraw(int[] processorData, double power) {
        editSystemGraphPanel.getSystemGraphPanel().addProcessorToDraw(processorData[0], power, processorData[1], processorData[2]);
        drawProcessorConnections();
    }

    public void editTaskToDraw(int[] taskData, double weight) {
        editTaskGraphPanel.getTaskGraphPanel().editTaskToDraw(taskData[0], weight, taskData[2], taskData[3]);
        drawConnections();
    }

    private void setSystem() {

    }

    private void addEditPanels() {
        editTaskGraphPanel = new EditTaskGraphPanel(workManager);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Редактор задач", editTaskGraphPanel);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
//        add(editTaskGraphPanel, gridBagConstraints);

        editSystemGraphPanel = new EditSystemGraphPanel(workManager);
        tabbedPane.add("Редактор процесів", editSystemGraphPanel);
//        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 0, 1, 1, 1);
//        add(editSystemGraphPanel, gridBagConstraints);
        add(tabbedPane, gridBagConstraints);
    }

    private void setLayoutParameters() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }

    private void setSize_Position() {
//        setResizable(false);
        setTitle("PZKS-2 Tsushko Ruslan");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width * 7 / 8;
        int frameHeight = screenSize.height * 7 / 8;
//        frameWidth /= 2;//remove for one monitor
        int leftCornerH = (screenSize.height - frameHeight) / 2;
//        int leftCornerW = (screenSize.width - frameWidth) / 2; //for one monitor
        int leftCornerW = 40;
        setBounds(leftCornerW, leftCornerH, frameWidth, frameHeight);
        setLayout(new BorderLayout());
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void removeAllTask() {
        editTaskGraphPanel.getTaskGraphPanel().removeAllTasks();
        getTaskGraphPanel().setConnectivity(0.0);
        editTaskGraphPanel.repaint();
    }

    public void removeAllProcessors() {
        editSystemGraphPanel.getSystemGraphPanel().removeAllProcessors();
        editSystemGraphPanel.repaint();
    }

    public EditTaskGraphPanel getEditTaskGraphPanel() {
        return editTaskGraphPanel;
    }

    public EditSystemGraphPanel getEditSystemTaskGraphPanel() {
        return editSystemGraphPanel;
    }

    public TaskGraphPanel getTaskGraphPanel() {
        return editTaskGraphPanel.getTaskGraphPanel();
    }

    public SystemGraphPanel getSystemGraphPanel() {
        return editSystemGraphPanel.getSystemGraphPanel();
    }
}
