package gui.task;

import gui.task.listeners.OpenTaskFromFileListener;
import gui.task.listeners.SaveTaskToFileListener;
import utils.WorkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskMenu {

    public static JMenu getMenu(WorkManager workManager) {
        JMenu taskMenu = new JMenu("Задача");
        JMenu newTask = new JMenu("Новий граф задачі");

        JMenuItem taskEditor = new JMenuItem("Редактор графа");
        taskEditor.addActionListener(actionEvent -> {
            workManager.removeAllTask();
        });
        newTask.add(taskEditor);
        JMenuItem taskGenerator = new JMenuItem("Генератор задач");
        taskGenerator.addActionListener(actionEvent -> {
            workManager.showGeneratingTasksFrame();
        });
        newTask.add(taskGenerator);

        JMenuItem openTaskFromFile = new JMenuItem("Відкрити");
        openTaskFromFile.addActionListener(new OpenTaskFromFileListener(workManager));

        JMenuItem saveTaskToFile = new JMenuItem("Зберегти");
        saveTaskToFile.addActionListener(new SaveTaskToFileListener(workManager));

        taskMenu.add(newTask);
        taskMenu.add(openTaskFromFile);
        taskMenu.add(saveTaskToFile);
        return taskMenu;
    }
}
