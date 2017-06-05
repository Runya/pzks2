package gui.system;

import gui.system.listeners.SystemGraphMouseListener;
import gui.system.listeners.SystemGraphMouseMotionListener;
import utils.WorkManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Vadim Petruk  on 2/10/14.
 */
//SystemPanel
//EditSystemTools
public class EditSystemGraphPanel extends JPanel {

    private WorkManager workManager;

    private SystemToolType activeSystemToolType = EditSystemGraphPanel.SystemToolType.MOUSE;

    private SystemGraphPanel systemGraphPanel;
    private EditSystemTools editSystemTools;

    public EditSystemGraphPanel(WorkManager workManager) {
        this.workManager = workManager;

        setLayoutParameters();
        editSystemTools = new EditSystemTools(this);
        add(editSystemTools, BorderLayout.EAST);

        addSystemPanel();

    }

    private void addSystemPanel() {
        systemGraphPanel = new SystemGraphPanel();
        systemGraphPanel.addMouseListener(new SystemGraphMouseListener(workManager.getSystemHandlerManager()));
        systemGraphPanel.addMouseMotionListener((new SystemGraphMouseMotionListener(workManager.getSystemHandlerManager())));

        add(systemGraphPanel, BorderLayout.CENTER);
    }

    private void setLayoutParameters() {
        setBorder(new TitledBorder("Граф системи"));
        setLayout(new BorderLayout());
    }

    public SystemGraphPanel getSystemGraphPanel() {
        return systemGraphPanel;
    }

    public SystemToolType getActiveSystemToolType() {
        return activeSystemToolType;
    }

    public void setActiveSystemToolType(SystemToolType activeSystemToolType) {
        this.activeSystemToolType = activeSystemToolType;
    }

    public enum SystemToolType {
        MOUSE, NEW_VERTEX, CONNECTION, REMOVE
    }
}
