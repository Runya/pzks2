package gui.modeling.gant;

import entities.computerSystem.Processor;
import entities.task.Task;
import entities.task.TaskGraph;
import gui.GridBagLayoutConfigurator;
import modelling.AlgorithmType;
import modelling.GantTasks;
import modelling.TaskQueueUtils;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class GantGraphFrame extends JFrame {
    public final int MIN_WIDTH = 700;
    public final int MIN_HEIGHT = 400;
    public int MAX_WIDTH;
    public int MAX_HEIGHT;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private WorkManager workManager;
    private QueueChoicePanel queuesChoicePanel;
    private QueuePanel queuePanel;
    private AlgorithmChoicePanel algorithmsChoicePanel;
    private GantPanel gantPanel;
    private TaskQueueUtils.TaskQueueType taskQueueTypeDefault = TaskQueueUtils.TaskQueueType.CRITICAL_WAY_FIRST_DECR_NUMBER_5;
    private AlgorithmType algorithmTypeDefault = AlgorithmType.FIRST_FREE_PROCESSOR_2;
    private TaskQueueUtils.TaskQueueType taskQueueType;
    private AlgorithmType algorithmType;

    private StatisticPanel statisticPanel;

    public GantGraphFrame(WorkManager workManager) throws HeadlessException {
        this.workManager = workManager;
        setSize_Position();
        setLayoutParameters();
        createPanels(workManager);
        addPanels();

        setVisible(true);
    }

    private void createPanels(WorkManager workManager) {
        queuePanel = new QueuePanel(workManager);
        queuePanel.setQueue(taskQueueTypeDefault);
        queuesChoicePanel = new QueueChoicePanel(workManager, queuePanel, this);
        algorithmsChoicePanel = new AlgorithmChoicePanel(workManager, this);
        gantPanel = new GantPanel(this, getGantTasks(workManager));
        statisticPanel = new StatisticPanel(getKy(), getKe());
    }

    private void addPanels() {
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
        add(queuesChoicePanel, gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 0, 1, 1, 1);
        add(algorithmsChoicePanel, gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 1, 2, 1, 1);
        add(queuePanel, gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 2, 2, 1, 100);
        add(gantPanel, gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 3, 2, 1, 1);
        add(statisticPanel, gridBagConstraints);
    }

    private GantTasks getGantTasks(WorkManager workManager) {
        TaskGraph taskGraph = workManager.getTaskGraph();
        taskQueueType = taskQueueType == null ? taskQueueTypeDefault : taskQueueType;
        algorithmType = algorithmType == null ? algorithmTypeDefault : algorithmType;
        int[] queue = TaskQueueUtils.getQueue(taskGraph, taskQueueType);
        return new GantTasks(taskGraph, workManager.getComputerSystem(), queue, algorithmType);
    }

    public void repaintGantPanel(TaskQueueUtils.TaskQueueType taskQueueType) {
        this.taskQueueType = taskQueueType;
        if (algorithmType != null) repaintGantPanel(taskQueueType, algorithmType);
    }

    public void repaintGantPanel(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
        if (taskQueueType != null) repaintGantPanel(taskQueueType, algorithmType);
    }

    public void repaintGantPanel(TaskQueueUtils.TaskQueueType taskQueueType, AlgorithmType algorithmType) {
        gantPanel.setGantTasks(getGantTasks(workManager));
        repaintStatisticPanel();
        repaint();
    }

    private void setSize_Position() {
//        setResizable(false);
        setTitle("Моделювання");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        MAX_WIDTH = screenSize.width;
        MAX_HEIGHT = screenSize.height;
//        int frameWidth = screenSize.width * 7 / 8;
//        int frameHeight = screenSize.height * 7 / 8;
//        frameWidth /= 2;//remove for one monitor
        int frameWidth = MIN_WIDTH;
        int frameHeight = MIN_HEIGHT;
        int leftCornerH = (screenSize.height - frameHeight) / 2;
//        int leftCornerW = (screenSize.width - frameWidth) / 2; //for one monitor
        int leftCornerW = 40;
        setBounds(leftCornerW, leftCornerH, frameWidth, frameHeight);
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setLayoutParameters() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }

    private void repaintStatisticPanel() {
        statisticPanel.setData(getKy(), getKe());
    }

    private double getKy() {
        GantTasks gantTasks = gantPanel.getGantTasks();
        double t1 = 0.0;
        for (Task task : workManager.getTaskGraph().getTasks()) {
            t1 += task.getWeight() / Processor.DEFAULT_POWER;
        }
        return (t1 / gantTasks.numberOfSteps());
    }

    private double getKe() {
        return getKy() / workManager.getComputerSystem().getProcessors().size();
    }
}
