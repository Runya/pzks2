package gui.system.listeners;

import utils.WorkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class SaveSystemToFileListener implements ActionListener {
    private WorkManager workManager;

    public SaveSystemToFileListener(WorkManager workManager) {
        this.workManager = workManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Зберегти");
        fileChooser.setCurrentDirectory(new File("saved_objects"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.exists()) {
                workManager.saveSystemGraphToFile(file.getAbsolutePath());
            } else {
                int n = JOptionPane.showConfirmDialog(null,
                        "Перезаписати файл? Дані цього файла будуть втрачені!",
                        "Файл існує!",
                        JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    workManager.saveSystemGraphToFile(file.getAbsolutePath());
                }
            }
        }
    }
}
