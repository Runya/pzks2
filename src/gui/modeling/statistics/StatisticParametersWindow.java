package gui.modeling.statistics;

import gui.GridBagLayoutConfigurator;
import utils.StatisticManager;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hadgehog on 21.02.14.
 */
public class StatisticParametersWindow extends JDialog {
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private WorkManager workManager;

    public StatisticParametersWindow(WorkManager workManager) {
        this.workManager = workManager;
        setSize_Position();
        setLayoutParameters();

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
        add(new JLabel("      Мінімальна кількість задач:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 0, 1, 1, 1);
        JTextField minTasks = new JTextField("" + StatisticManager.MIN_TASKS);
        add(minTasks, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 1, 1, 1, 1);
        add(new JLabel("      Максимальна кількість задач:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 1, 1, 1, 1);
        JTextField maxTasks = new JTextField("" + StatisticManager.MAX_TASKS);
        add(maxTasks, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 2, 1, 1, 1);
        add(new JLabel("      Крок нарощування кількості вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 2, 1, 1, 1);
        JTextField tasksStep = new JTextField("" + StatisticManager.TASKS_STEP);
        add(tasksStep, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 3, 1, 1, 1);
        add(new JLabel("      Початкова зв’язність:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 3, 1, 1, 1);
        JTextField initialConnectivity = new JTextField("" + StatisticManager.INITIAL_CONNECTIVITY);
        add(initialConnectivity, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 4, 1, 1, 1);
        add(new JLabel("      Кінцева зв’язність:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 4, 1, 1, 1);
        JTextField finalConnectivity = new JTextField("" + StatisticManager.FINAL_CONNECTIVITY);
        add(finalConnectivity, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 5, 1, 1, 1);
        add(new JLabel("      Крок нарощування  зв’язності:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 5, 1, 1, 1);
        JTextField connectivityStep = new JTextField("" + StatisticManager.CONNECTIVITY_STEP);
        add(connectivityStep, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 6, 1, 1, 1);
        add(new JLabel("      Мінімальна вага вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 6, 1, 1, 1);
        JTextField minTasksWeight = new JTextField("" + StatisticManager.MIN_TASK_WEIGHT);
        add(minTasksWeight, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 7, 1, 1, 1);
        add(new JLabel("      Максимальна вага вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 7, 1, 1, 1);
        JTextField maxTasksWeight = new JTextField("" + StatisticManager.MAX_TASK_WEIGHT);
        add(maxTasksWeight, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 8, 1, 1, 1);
        add(new JLabel("      Кількість графів задач :"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 8, 1, 1, 1);
        JTextField taskGraphNumber = new JTextField("" + StatisticManager.TASK_GRAPH_NUMBER);
        add(taskGraphNumber, gridBagConstraints);

        JButton okButton = new JButton("Зберегти");
        okButton.addActionListener(e -> {
            try {
                //НІКОЛИ НЕ РОБІТЬ ТАК!!!
                StatisticManager.MIN_TASKS = getInt(minTasks.getText());
                StatisticManager.MAX_TASKS = getInt(maxTasks.getText());
                StatisticManager.TASKS_STEP = getInt(tasksStep.getText());
                StatisticManager.INITIAL_CONNECTIVITY = getDouble(initialConnectivity.getText());
                StatisticManager.FINAL_CONNECTIVITY = getDouble(finalConnectivity.getText());
                StatisticManager.CONNECTIVITY_STEP = getDouble(connectivityStep.getText());
                StatisticManager.MIN_TASK_WEIGHT = getDouble(minTasksWeight.getText());
                StatisticManager.MAX_TASK_WEIGHT = getDouble(maxTasksWeight.getText());
                StatisticManager.TASK_GRAPH_NUMBER = getInt(taskGraphNumber.getText());
                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Невірно введене значення! ");
            }
        });
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 9, 1, 1, 1);
        add(okButton, gridBagConstraints);
        JButton cancelButton = new JButton("Відмінити");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 9, 1, 1, 1);
        add(cancelButton, gridBagConstraints);
        setVisible(true);
    }

    private void setLayoutParameters() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void setSize_Position() {
        setTitle("Параметри статистики");
        setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 3;
        int frameHeight = screenSize.height / 3;
        int leftCornerH = (screenSize.height - frameHeight) / 2;
        int leftCornerW = (screenSize.width - frameWidth) / 2;
        setBounds(leftCornerW, leftCornerH, frameWidth, frameHeight);
        setLayout(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private int getInt(String s) throws NumberFormatException {
        return Integer.parseInt(s.replace(" ",""));
    }

    private double getDouble(String s) throws NumberFormatException {
        return Double.parseDouble(s.replace(" ",""));
    }
}
