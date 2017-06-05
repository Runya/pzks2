package gui.modeling.gant;

import modelling.AlgorithmType;
import utils.WorkManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class AlgorithmChoicePanel extends JPanel {
    private ButtonGroup buttonGroup = new ButtonGroup();
    private List<JRadioButton> jRadioButtons = new ArrayList<>();

    public AlgorithmChoicePanel(WorkManager workManager, final GantGraphFrame gantGraphFrame) {
        setLayoutsParameters();
        for (AlgorithmType algorithmType : AlgorithmType.values()) {
            JRadioButton jRadioButton = new JRadioButton(algorithmType.toString());
            jRadioButton.addActionListener(actionEvent -> {
                gantGraphFrame.repaintGantPanel(algorithmType);
            });
            jRadioButtons.add(jRadioButton);
            buttonGroup.add(jRadioButton);
        }

        for (JRadioButton jRadioButton : jRadioButtons) {
            add(jRadioButton);
        }
        jRadioButtons.get(0).setSelected(true);
    }

    private void setLayoutsParameters() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Алгоритм призначення"));
    }
}
