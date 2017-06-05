package gui.modeling.gant;

import modelling.TaskQueueUtils;
import utils.WorkManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class QueueChoicePanel extends JPanel {
    private ButtonGroup buttonGroup = new ButtonGroup();
    private List<JRadioButton> jRadioButtons = new ArrayList<>();

    public QueueChoicePanel(WorkManager workManager, final QueuePanel queuePanel, final GantGraphFrame gantGraphFrame) {
        setLayoutsParameters();
        for (TaskQueueUtils.TaskQueueType taskQueueType : TaskQueueUtils.TaskQueueType.values()) {
            JRadioButton jRadioButton = new JRadioButton(taskQueueType.toString());
            jRadioButton.addActionListener(actionEvent -> {
                queuePanel.setQueue(taskQueueType);
                gantGraphFrame.repaintGantPanel(taskQueueType);
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
        setBorder(new TitledBorder("Алгоритм формування черги задач"));
    }
}
