package gui.modeling;

import gui.GridBagLayoutConfigurator;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hadgehog on 21.02.14.
 */
public class ProcessorParametersWindow extends JDialog {
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private WorkManager workManager;

    public ProcessorParametersWindow(WorkManager workManager) {
        this.workManager = workManager;
        setSize_Position();
        setLayoutParameters();

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
        add(new JLabel("      Продуктивність процесорів:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 0, 1, 1, 1);
        JTextField processorPower = new JTextField("" + workManager.getProcessorPower());
        add(processorPower, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 1, 1, 1, 1);
        add(new JLabel("      Пропускна здатність:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 1, 1, 1, 1);
        JTextField linkBandwidth = new JTextField("" + workManager.getLinkBandwidth());
        add(linkBandwidth, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 2, 1, 1, 1);
//        add(new JLabel("      Число фізичних лінків:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 2, 1, 1, 1);
        JTextField numberOfPhysicalLinks = new JTextField("" + workManager.getPhysicalLinkNumber());
//        add(numberOfPhysicalLinks, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 3, 1, 1, 1);
        add(new JLabel("      Дуплексність/напівдуплексність:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 3, 1, 1, 1);
        JCheckBox duplex = new JCheckBox("Зв'язки дуплексні", workManager.getLinkDuplex());
        add(duplex, gridBagConstraints);

        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 4, 1, 1, 1);
//        add(new JLabel("      Процесор вводу/виводу:"), gridBagConstraints);
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 1, 4, 1, 1, 1);
        JCheckBox inOutProcessor = new JCheckBox("Процесор ВВ", workManager.getInOutProcessor());
//        add(inOutProcessor, gridBagConstraints);

        JButton okButton = new JButton("Зберегти");
        okButton.addActionListener(e -> {
            try {
                double newPower = Double.parseDouble(processorPower.getText());
                workManager.setProcessorsPower(newPower);
                double newBandwidth = Double.parseDouble(linkBandwidth.getText());
                workManager.setLinkBandwidth(newBandwidth);
                workManager.setDuplex(duplex.isSelected());
                int newPhysicalLinkNumber = Integer.parseInt(numberOfPhysicalLinks.getText());
                workManager.setPhysicalLinkNumber(newPhysicalLinkNumber);
                workManager.setInOutProcessor(inOutProcessor.isSelected());
                dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Невірно введене значення! ");
            }
        });
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 6, 1, 1, 1);
        add(okButton, gridBagConstraints);
        JButton cancelButton = new JButton("Відмінити");
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
        setTitle("Параметри процесорів");
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
