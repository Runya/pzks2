package gui.modeling;

import gui.modeling.gant.GantGraphFrame;
import modelling.TaskQueueUtils;
import utils.ModellingManager;
import utils.WorkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class ModellingMenu {

    public static JMenu getMenu(WorkManager workManager) {
        JMenu modellingMenu = new JMenu("Моделювання");
        JMenuItem PEParameters = new JMenuItem("Параметри процесорів");
        PEParameters.addActionListener(e -> workManager.showPEParametersFrame());
        JMenuItem gantDiagram = new JMenuItem("Діаграма Ганта");
        gantDiagram.addActionListener(actionEvent -> {
            if (workManager.getComputerSystem().getSystemConnectivity())
                if (workManager.getComputerSystem().getProcessors().size() > 0)
                    new GantGraphFrame(workManager);
                else
                    JOptionPane.showMessageDialog(null, "У системі відсутні процесорні елементи!!");
            else
                JOptionPane.showMessageDialog(null, "Система не зв’язна!");
        });
        JMenuItem quieType13 = new JMenuItem("Queue type 13, random");
        quieType13.addActionListener(actionEvent -> {
            int[] queue = TaskQueueUtils.getQueue(workManager.getTaskGraph(), TaskQueueUtils.TaskQueueType.RANDOM_WAY_13);
            TaskQueueUtils.printQueue(queue, TaskQueueUtils.TaskQueueType.RANDOM_WAY_13);
        });

        JMenuItem quieType3 = new JMenuItem("Queue type 3");
        quieType3.addActionListener(actionEvent -> {
            int[] queue = TaskQueueUtils.getQueue(workManager.getTaskGraph(), TaskQueueUtils.TaskQueueType.QUEIE_3_CRITICAL_DECRES_WAY);
            TaskQueueUtils.printQueue(queue, TaskQueueUtils.TaskQueueType.QUEIE_3_CRITICAL_DECRES_WAY);
        });

        JMenuItem quieType5 = new JMenuItem("Queue type 5");
        quieType5.addActionListener(actionEvent -> {
            System.out.println("nice la");
            int[] queue = TaskQueueUtils.getQueue(workManager.getTaskGraph(), TaskQueueUtils.TaskQueueType.CRITICAL_WAY_FIRST_DECR_NUMBER_5);
            TaskQueueUtils.printQueue(queue, TaskQueueUtils.TaskQueueType.CRITICAL_WAY_FIRST_DECR_NUMBER_5);
        });

        JMenu quiues = new JMenu("Черги");
        quiues.add(quieType3);
        quiues.add(quieType5);
        quiues.add(quieType13);

        JMenu statistic = new JMenu("Статистика");

        JMenuItem statisticParameters = new JMenuItem("Параметри");
        statisticParameters.addActionListener(e -> workManager.showStatisticParametersFrame());
        JMenuItem statisticResults = new JMenuItem("Результати");
        statisticResults.addActionListener(e -> workManager.showStatisticFrame());
        statistic.add(statisticParameters);
        statistic.add(statisticResults);

        JMenuItem queueModelling = new JMenuItem("Сформувати черги задач");
        queueModelling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ModellingManager.createQueue(workManager.getTaskGraph());
            }
        });

        modellingMenu.add(PEParameters);
        modellingMenu.add(gantDiagram);
        modellingMenu.add(statistic);
        modellingMenu.add(queueModelling);
        modellingMenu.add(quiues);
        return modellingMenu;
    }
}
