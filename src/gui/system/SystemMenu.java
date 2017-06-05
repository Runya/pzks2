package gui.system;

import gui.system.listeners.OpenSystemFromFileListener;
import gui.system.listeners.SaveSystemToFileListener;
import utils.WorkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class SystemMenu {
    public static JMenu getMenu(WorkManager workManager) {
        JMenu systemMenu = new JMenu("Система");
        JMenuItem newSystem = new JMenuItem("Новий граф");
        newSystem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                workManager.removeAllProcessors();
            }
        });

        JMenuItem openSystemFromFile = new JMenuItem("Відкрити");
        openSystemFromFile.addActionListener(new OpenSystemFromFileListener(workManager));

        JMenuItem saveSystemToFileTask = new JMenuItem("Зберегти");
        saveSystemToFileTask.addActionListener(new SaveSystemToFileListener(workManager));

        systemMenu.add(newSystem);
        systemMenu.add(openSystemFromFile);
        systemMenu.add(saveSystemToFileTask);
        return systemMenu;
    }
}
