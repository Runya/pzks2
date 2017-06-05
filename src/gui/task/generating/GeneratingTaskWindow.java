package gui.task.generating;

import gui.GridBagLayoutConfigurator;
import utils.TaskGraphGenerator;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hadgehog on 21.02.14.
 */
public class GeneratingTaskWindow extends JDialog {
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private final double MIN_TASK_WEIGHT = 1.0;
    private final double MAX_TASK_WEIGHT = 10.0;
    private final int TASK_NUMBER = 8;
    private final double MIN_CONNECTION_WEIGHT = 1.0;
    private final double MAX_CONNECTION_WEIGHT = 10.0;
    private final double CONNECTIVITY = 0.4;

    private WorkManager workManager;

    public GeneratingTaskWindow(WorkManager workManager) {
        this.workManager = workManager;
        setSize_Position();
        setLayoutParameters();

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
        add(new JLabel("      Мінімальна вага вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 0, 1, 1, 1);
        JTextField minTaskWeightField = new JTextField("" + MIN_TASK_WEIGHT);
        add(minTaskWeightField, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 1, 1, 1, 1);
        add(new JLabel("      Максимальна вага вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 1, 1, 1, 1);
        JTextField maxTaskWeightField = new JTextField("" + MAX_TASK_WEIGHT);
        add(maxTaskWeightField, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 2, 1, 1, 1);
        add(new JLabel("      Кількість вершин:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 2, 1, 1, 1);
        JTextField taskNumberField = new JTextField("" + TASK_NUMBER);
        add(taskNumberField, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 3, 1, 1, 1);
        add(new JLabel("      Зв’язність графу:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 3, 1, 1, 1);
        JTextField connectivityField = new JTextField("" + CONNECTIVITY);
        add(connectivityField, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 4, 1, 1, 1);
        add(new JLabel("      Мінімальна вага дуг:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 4, 1, 1, 1);
        JTextField minConnectionWeightField = new JTextField("" + MIN_CONNECTION_WEIGHT);
        add(minConnectionWeightField, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 5, 1, 1, 1);
        add(new JLabel("      Максимальна вага дуг:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 5, 1, 1, 1);
        JTextField maxConnetionWeightField = new JTextField("" + MAX_CONNECTION_WEIGHT);
        add(maxConnetionWeightField, gridBagConstraints);

        JButton okButton = new JButton("Згенерувати");
        okButton.addActionListener(e -> {
            try {
                double minTaskWeight = Double.parseDouble(minTaskWeightField.getText());
                double maxTaskWeight = Double.parseDouble(maxTaskWeightField.getText());
                if (minTaskWeight > maxTaskWeight) throw new NumberFormatException("max must be more the min");
                int taskNumber = Integer.parseInt(taskNumberField.getText());
                double connectivity = Double.parseDouble(connectivityField.getText());
                double minConnectionWeight = Double.parseDouble(minConnectionWeightField.getText());
                double maxConnectionWeight = Double.parseDouble(maxTaskWeightField.getText());
                if (minTaskWeight > maxTaskWeight) throw new NumberFormatException("max must be more the min");
//                workManager.setLinkBandwidth(newBandwidth);
//                System.out.println("n = " + taskNumber + " minW = " + minTaskWeight + " maxW = " + maxTaskWeight);
                TaskGraphGenerator taskGraphGenerator = new TaskGraphGenerator(workManager.getTaskPanelMaxX(), workManager.getTaskPanelMaxY());
                taskGraphGenerator.setWM(workManager);
                workManager.setTaskGraph(taskGraphGenerator.generateTaskGraph(taskNumber, minTaskWeight, maxTaskWeight,
                        connectivity, minConnectionWeight, maxConnectionWeight));
//                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Невірно введене значення! " + nfe.getMessage());
            }
        });
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 6, 1, 1, 1);
        add(okButton, gridBagConstraints);
        JButton cancelButton = new JButton("Ок");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 6, 1, 1, 1);
        add(cancelButton, gridBagConstraints);
        setVisible(true);
    }

    private void setLayoutParameters() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void setSize_Position() {
        setTitle("Генератор задач");
        setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 3;
        int frameHeight = screenSize.height / 3;
        int leftCornerH = (screenSize.height - frameHeight) / 2;
        int leftCornerW = (screenSize.width - frameWidth) / 2;
        setBounds(leftCornerW, leftCornerH, frameWidth, frameHeight);
        setLayout(new BorderLayout());
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
