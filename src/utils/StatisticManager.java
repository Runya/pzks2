package utils;

import entities.computerSystem.ComputerSystem;
import entities.task.Task;
import entities.task.TaskGraph;
import modelling.AlgorithmType;
import modelling.GantTasks;
import modelling.TaskQueueUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hadgehog on 27.05.2014.
 */
public class StatisticManager {
    public static double eps = 100.0;

    public static int MIN_TASKS = 16;
    public static int MAX_TASKS = 16;
    public static int TASKS_STEP = 1;
    public static double INITIAL_CONNECTIVITY = 0.1;
    public static double FINAL_CONNECTIVITY = 0.9;
    public static double CONNECTIVITY_STEP = 0.1;
    public static double MIN_TASK_WEIGHT = 1.0;
    public static double MAX_TASK_WEIGHT = 10.0;
    public static int TASK_GRAPH_NUMBER = 1;

    private static Map<TaskQueueUtils.TaskQueueType, String> qTypeStr;
    private static Map<AlgorithmType, String> typeStr;

    static {
        qTypeStr = new HashMap<>();
        qTypeStr.put(TaskQueueUtils.TaskQueueType.CRITICAL_WAY_FIRST_DECR_NUMBER_5, "Q");
        typeStr = new HashMap<>();
        typeStr.put(AlgorithmType.FIRST_FREE_PROCESSOR_2, "A2");
        typeStr.put(AlgorithmType.NEIGHBOUR_WITHOUT_NOTIFICATION_3, "A5");
    }

    public static double getKy(GantTasks gantTasks, Task[] tasks) {
        double t1 = 0.0;
        for (Task task : tasks) {
            t1 += task.getWeight();//for processor_power = 1
        }
        double tn = gantTasks.numberOfSteps();
        return t1 / tn;
    }

    private static double getKe(GantTasks gantTasks, Task[] tasks, int n) {
        double ke = getKy(gantTasks, tasks);
        return (ke / (double) n);
    }

    public static double getKv(GantTasks gantTasks, TaskGraph taskGraph) {
        double criticalWeightWay = 0;
        int[][] transposedMatrix = TaskQueueUtils.transposeMatrix(taskGraph.getAdjacencyMatrix());
        for (Task task : taskGraph.getTasks()) {
            int pos = taskGraph.getTaskPosById(task.getId());
            double criticalTaskWeightWay = TaskQueueUtils.getCriticalWayFromStartByTime(taskGraph, pos, 0, transposedMatrix);
            if (criticalTaskWeightWay > criticalWeightWay) criticalWeightWay = criticalTaskWeightWay;
        }
        return criticalWeightWay / gantTasks.numberOfSteps();
    }

    public static double getTn(GantTasks gantTasks) {
        return gantTasks.numberOfSteps();
    }

    public static String getResult(WorkManager workManager) {
        StringBuilder resultBld = new StringBuilder();
        TaskGraphGenerator generator = new TaskGraphGenerator();
        for (AlgorithmType algorithmType : AlgorithmType.values()) {
            for (TaskQueueUtils.TaskQueueType queueType : TaskQueueUtils.TaskQueueType.values()) {
                for (double i = INITIAL_CONNECTIVITY; i < FINAL_CONNECTIVITY; i += CONNECTIVITY_STEP) {
//            resultBld.append(Math.round(i*10)/10.0).append("\t");
                    resultBld.append(qTypeStr.get(queueType)).append(" ").append(typeStr.get(algorithmType)).append("\t").append(Math.round(i * 10) / 10.0).append("\t");
                    double ke = 0.0;
                    double ky = 0.0;
                    double kv = 0.0;
                    double tn = 0;
                    for (int j = 0; j < TASK_GRAPH_NUMBER; j++) {
                        int taskNumber = random(MIN_TASKS, MAX_TASKS);
                        TaskGraph taskGraph = generator.generateTaskGraph(taskNumber, MIN_TASK_WEIGHT, MAX_TASK_WEIGHT, i, 1, 1);
                        ComputerSystem computerSystem = workManager.getComputerSystem();
                        int[] queue = TaskQueueUtils.getQueue(taskGraph, queueType);
                        GantTasks gantTasks = new GantTasks(taskGraph, computerSystem, queue, algorithmType);
                        ky += getKy(gantTasks, taskGraph.getTasks().toArray(new Task[1]));
                        ke += getKe(gantTasks, taskGraph.getTasks().toArray(new Task[1]), computerSystem.getProcessors().size());
                        kv += getKv(gantTasks, taskGraph);
                        tn += getTn(gantTasks);
                    }
                    ke /= TASK_GRAPH_NUMBER;
                    ky /= TASK_GRAPH_NUMBER;
                    kv /= TASK_GRAPH_NUMBER;
                    tn /= TASK_GRAPH_NUMBER;
                    ke = Math.round((ke) * eps) / eps;
                    ky = Math.round((ky) * eps) / eps;
                    kv = Math.round((kv) * eps) / eps;
                    tn = Math.round((tn) * eps) / eps;
                    resultBld.append(ke).append("\t").append(ky).append("\t").append(kv).append("\t");
                    resultBld.append("\n");
                }
            }
        }
        return resultBld.toString();//.replaceAll(".",",");
    }

    public static double random(double min, double max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }

    public static int random(int min, int max) {
        return (int) random((double) min, (double) max);
    }
}
