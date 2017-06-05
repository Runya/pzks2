package utils;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;
import gui.MainFrame;
import gui.modeling.ProcessorParametersWindow;
import gui.modeling.statistics.StatisticFrame;
import gui.modeling.statistics.StatisticParametersWindow;
import gui.system.EditSystemGraphPanel;
import gui.system.SystemGraphPanel;
import gui.system.mouseHandlers.SystemHandlerManager;
import gui.task.EditTaskGraphPanel;
import gui.task.TaskGraphPanel;
import gui.task.generating.GeneratingTaskWindow;
import gui.task.mouseHandlers.TaskHandlerManager;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by hadgehog on 16.02.14.
 */
public class WorkManager {
    private MainFrame mainFrame;
    private TaskGraph taskGraph;
    private ComputerSystem computerSystem;
    private TaskHandlerManager taskHandlerManager;
    private SystemHandlerManager systemHandlerManager;

    public WorkManager(TaskGraph taskGraph, ComputerSystem computerSystem) {
        this.taskGraph = taskGraph;
        this.computerSystem = computerSystem;
        taskHandlerManager = new TaskHandlerManager();
        systemHandlerManager = new SystemHandlerManager();
        this.mainFrame = new MainFrame(this);
        taskHandlerManager.setData(this);
        systemHandlerManager.setData(this);
    }

    public WorkManager() {
        this(new TaskGraph(), new ComputerSystem());
    }

    public int[][] getTasksData() {
        return taskGraph.getTasksData();
    }

    public int[][] getProcessorsData() {
        return computerSystem.getProcessorData();
    }

    public double getTaskWeight(int id) {
        return taskGraph.getTaskWeightById(id);
    }

    public double getProcessorPower(int id) {
        return computerSystem.getProcessorPower(id);
    }

    public double getProcessorPower() {
        return computerSystem.getProcessorPower();
    }

    public double getLinkBandwidth() {
        return computerSystem.getLinkBandwidth();
    }

    public int getPhysicalLinkNumber() {
        return computerSystem.getPhysicalLinkNumber();
    }

    public boolean getLinkDuplex() {
        return computerSystem.getDuplex();
    }

    public boolean getInOutProcessor() {
        return computerSystem.getInOutProcessor();
    }

    public void setInOutProcessor(boolean inOut) {
        computerSystem.setInOutProcessor(inOut);
    }

    public void setLinkBandwidth(double newBandwidth) {
        computerSystem.setLinkBandwidth(newBandwidth);
        mainFrame.drawSystem();
    }

    public void setProcessorsPower(double newPower) {
        computerSystem.setProcessorsPower(newPower);
        mainFrame.drawSystem();
    }

    public int[][] getConnectionsData() {
        return taskGraph.getConnectionsData();
    }

    public int[][] getSystemConnectionsData() {
        return computerSystem.getConnectionsData();
    }

    public int[][] getProcessorConnectionsData() {
        return computerSystem.getConnectionsData();
    }

    public void addTask(int x, int y) {
        int id = taskGraph.addTask(x, y);
        mainFrame.addTaskToDraw(taskGraph.getTaskData(id), taskGraph.getTaskWeightById(id));
        setGraphPanelConnectivity();
        mainFrame.repaint();
    }

    public void addProcessor(int x, int y) {
        int id = computerSystem.addProcessor(x, y);
        mainFrame.addProcessorToDraw(computerSystem.getProcessorData(id), computerSystem.getProcessorPower(id));
        mainFrame.repaint();
        setSystemConnectivity();
    }

    public void removeTask(int id) {
        taskGraph.removeTask(id);
        setGraphPanelConnectivity();
        mainFrame.drawTasks();
    }

    public void removeProcessor(int id) {
        computerSystem.removeProcessor(id);
        mainFrame.drawSystem();
        setSystemConnectivity();
    }

    public void addConnection(int fromTaskId, int toTaskId) {
        if (taskGraph.addDepend(fromTaskId, toTaskId)) {
            setGraphPanelConnectivity();
            mainFrame.drawTasks();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Циклічний зв'язок!");
        }
    }

    public void removeConnection(int fromTaskId, int toTaskId) {
        taskGraph.removeDepend(toTaskId, fromTaskId);
        setGraphPanelConnectivity();
        mainFrame.drawTasks();
    }

    public void removeSystemConnection(int fromTaskId, int toTaskId) {
        computerSystem.removeDepend(toTaskId, fromTaskId);
        mainFrame.drawSystem();
        setSystemConnectivity();
    }

    public void editTask(int id, int newWeight, int newX, int newY) {
        taskGraph.setTaskData(id, newWeight, newX, newY);
        setGraphPanelConnectivity();
        mainFrame.editTaskToDraw(taskGraph.getTaskData(id), taskGraph.getTaskWeightById(id));
        mainFrame.repaint();
    }

    public void editTask(int id, int newX, int newY) {
        taskGraph.setTaskData(id, newX, newY);
        setGraphPanelConnectivity();
    }

    public void editProcessor(int id, int newX, int newY) {
        computerSystem.setProcessorData(id, newX, newY);
//        mainFrame.drawSystem();
    }

    public void saveTaskGraphToFile(String fileName) {
        taskGraph.saveToFile(fileName);
    }

    public TaskGraph openTaskGraphFromFile(String fileName) {
        try {
            taskGraph.readFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();//dialog window
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//dialog window
        }
        setGraphPanelConnectivity();
        mainFrame.drawTasks();
//        mainFrame.repaint();
        return taskGraph;
    }

    public void openSystemGraphFromFile(String fileName) {
        try {
            computerSystem.readFromFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();//dialog window
        } catch (ClassNotFoundException e) {
            e.printStackTrace();//dialog window
        }
        mainFrame.drawSystem();
        setSystemConnectivity();
    }

    public void saveSystemGraphToFile(String fileName) {
        computerSystem.saveToFile(fileName);
    }

    public TaskHandlerManager getTaskHandlerManager() {
        return taskHandlerManager;
    }

    public SystemHandlerManager getSystemHandlerManager() {
        return systemHandlerManager;
    }

    public EditTaskGraphPanel getEditTaskGraphPanel() {
        return mainFrame.getEditTaskGraphPanel();
    }

    public EditSystemGraphPanel getEditSystemTaskGraphPanel() {
        return mainFrame.getEditSystemTaskGraphPanel();
    }

    public TaskGraphPanel getTaskGraphPanel() {
        return mainFrame.getTaskGraphPanel();
    }

    public SystemGraphPanel getSystemGraphPanel() {
        return mainFrame.getSystemGraphPanel();
    }

    public void removeAllTask() {
        mainFrame.removeAllTask();
        taskGraph.removeAllTask();
    }

    public void removeAllProcessors() {
        mainFrame.removeAllProcessors();
        computerSystem.removeAllProcessors();
        setSystemConnectivity();
    }

    public void setNewWeight(int id, double newWeight) {
        taskGraph.setNewWeight(id, newWeight);
        setGraphPanelConnectivity();
        mainFrame.getTaskGraphPanel().setNewWeight(id, newWeight);
        mainFrame.getEditTaskGraphPanel().repaint();
    }

    public void setNewPower(int id, double newWeight) {
        computerSystem.setNewPower(id, newWeight);
        mainFrame.getSystemGraphPanel().setNewPower(id, newWeight);
        mainFrame.getEditSystemTaskGraphPanel().repaint();
    }

    public double getTaskConnectionBandwidth(int fromID, int toId) {
        return taskGraph.getConnectionBandwidth(fromID, toId);
    }

    public double getSystemConnectionBandwidth(int fromID, int toId) {
        return computerSystem.getConnectionBandwidth(fromID, toId);
    }

    public void setNewBandwidth(int fromId, int toId, double newBandwidth) {
        taskGraph.setNewBandwidth(fromId, toId, newBandwidth);
        setGraphPanelConnectivity();
        mainFrame.getTaskGraphPanel().setNewBandwidth(fromId, toId, newBandwidth);
        mainFrame.getEditTaskGraphPanel().repaint();
    }

    public void setNewSystemConnectionBandwidth(int fromId, int toId, double newBandwidth) {
        computerSystem.setNewBandwidth(fromId, toId, newBandwidth);
        mainFrame.getSystemGraphPanel().setNewBandwidth(fromId, toId, newBandwidth);
        mainFrame.getEditSystemTaskGraphPanel().repaint();
    }

    public void addSystemConnection(int fromProcessorId, int toProcessorId) {
        if (computerSystem.addDepend(fromProcessorId, toProcessorId)) {
            mainFrame.drawSystem();
            setSystemConnectivity();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Зв'язок існує!");
        }
    }

    public void setSystemConnectivity() {
        boolean systemConnectivity = computerSystem.getSystemConnectivity();
        mainFrame.getSystemGraphPanel().setSystemConnectivity(systemConnectivity);
        mainFrame.getSystemGraphPanel().repaint();
    }

    public void showPEParametersFrame() {
        ProcessorParametersWindow processorParametersWindow = new ProcessorParametersWindow(this);
    }

    public void showStatisticParametersFrame() {
        new StatisticParametersWindow(this);
    }

    public void showStatisticFrame() {
        if (computerSystem.getSystemConnectivity())
            if (computerSystem.getProcessors().size() > 0)
                new StatisticFrame(this, StatisticManager.getResult(this));
            else
                JOptionPane.showMessageDialog(null, "У системі відсутні процесорні елементи!!");
        else
            JOptionPane.showMessageDialog(null, "Система не зв’язна!");
    }

    public void showGeneratingTasksFrame() {
        GeneratingTaskWindow generatingTaskWindow = new GeneratingTaskWindow(this);
    }

    public TaskGraph getTaskGraph() {
        return taskGraph;
    }

    public ComputerSystem getComputerSystem() {
        return computerSystem;
    }

    public void setTaskGraph(TaskGraph taskGraph) {
        this.taskGraph = taskGraph;
        setGraphPanelConnectivity();
        mainFrame.drawTasks();
    }

    public int getTaskPanelMaxX() {
        return getEditTaskGraphPanel().getTaskGraphPanel().getWidth();
    }

    public int getTaskPanelMaxY() {
        return getEditTaskGraphPanel().getTaskGraphPanel().getHeight();
    }

    public void setGraphPanelConnectivity() {
        getTaskGraphPanel().setConnectivity(taskGraph.getGraphConnectivity());
    }

    public void incTaskWeight(int taskId) {
        taskGraph.incTaskWeight(taskId);
    }

    public void setDuplex(boolean duplex) {
        computerSystem.setDuplex(duplex);
    }

    public void setPhysicalLinkNumber(int number) {
        computerSystem.setDefaultPhysicalLinkNumber(number);
    }
}
