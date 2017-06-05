package gui.system.listeners;

import utils.WorkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class OpenSystemFromFileListener implements ActionListener {
    private WorkManager workManager;

    public OpenSystemFromFileListener(WorkManager workManager) {
        this.workManager = workManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Відкрити");
        fileChooser.setCurrentDirectory(new File("saved_objects"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            workManager.openSystemGraphFromFile(file.getAbsolutePath());
        }
    }
}
