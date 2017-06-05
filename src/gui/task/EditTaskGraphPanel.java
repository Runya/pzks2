package gui.task;

import gui.task.listeners.TaskGraphMouseListener;
import gui.task.listeners.TaskGraphMouseMotionListener;
import utils.WorkManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */

//TaskPanel
//EditSystemTools
public class EditTaskGraphPanel extends JPanel {

    private WorkManager workManager;

    private TaskToolType activeTaskToolType = TaskToolType.MOUSE;

    private TaskGraphPanel taskGraphPanel;
    private EditTaskTools editTaskTools;

    public EditTaskGraphPanel(WorkManager workManager) {
        this.workManager = workManager;

        setLayoutParameters();

        editTaskTools = new EditTaskTools(this);
        add(editTaskTools, BorderLayout.WEST);

        addEditTaskPanel();
    }

    private void addEditTaskPanel() {
        taskGraphPanel = new TaskGraphPanel();
        taskGraphPanel.addMouseListener(new TaskGraphMouseListener(workManager.getTaskHandlerManager()));
        taskGraphPanel.addMouseMotionListener((new TaskGraphMouseMotionListener(workManager.getTaskHandlerManager())));
        add(taskGraphPanel, BorderLayout.CENTER);
    }

    private void setLayoutParameters() {
        setBorder(new TitledBorder("Граф задачі"));
        setLayout(new BorderLayout());
    }

    public TaskGraphPanel getTaskGraphPanel() {
        return taskGraphPanel;
    }

    public void setActiveTaskToolType(TaskToolType activeTaskToolType) {
        this.activeTaskToolType = activeTaskToolType;
    }

    public TaskToolType getActiveTaskToolType() {
        return activeTaskToolType;
    }

    public enum TaskToolType {
        MOUSE, NEW_VERTEX, CONNECTION, REMOVE
    }
}


