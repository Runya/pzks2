package gui.modeling.gant;

import modelling.TaskQueueUtils;
import utils.WorkManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class QueuePanel extends JPanel {
    private JLabel queueLabel;
    private WorkManager workManager;

    public QueuePanel(WorkManager workManager) {
        this.workManager = workManager;
        setLayoutsParameters();
        queueLabel = new JLabel("тут буде черга задач...");
        add(queueLabel);
    }

    private void setLayoutsParameters() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new TitledBorder("Черга задач"));
    }

    public void setQueue(TaskQueueUtils.TaskQueueType taskQueueType) {
        int[] queue = TaskQueueUtils.getQueue(workManager.getTaskGraph(), taskQueueType);
        queueLabel.setText(getQueueToString(queue));
    }

    private String getQueueToString(int[] queue) {
        String resultString = "";
        for (int i = 0; i < queue.length; i++) {
            resultString += queue[i];
            if (i != queue.length - 1) resultString += ", ";
        }
        return resultString;
    }
}
