package modelling;

import entities.task.Task;
import entities.task.TaskConnection;
import entities.task.TaskGraph;
import entities.task.TaskQueueData;
import utils.CollectionUtils;
import utils.comparators.TaskQueue1Comparator;
import utils.comparators.TaskQueue2Comparator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by hadgehog on 30.03.2014.
 */
public class TaskQueueUtils {
    private static Map<Integer, Integer> vertexNumberWays;
    private static Map<Integer, Double> timeFromStartWays;
    private static Map<Integer, Double> timeToEndWays;
    public static TaskGraph taskGraph;

    public static int[] getQueue(TaskGraph taskGraph, TaskQueueType taskQueueType) {
        TaskQueueUtils.taskGraph = taskGraph;
        switch (taskQueueType) {
            case RANDOM_WAY_13:
                return getQueue4(taskGraph);
            case CRITICAL_WAY_FIRST_DECR_NUMBER_5:
                return getQueue6(taskGraph);
            case QUEIE_3_CRITICAL_DECRES_WAY:
                return getQueue5(taskGraph);
        }
        return null;
    }

    private static int[] getQueue6(TaskGraph taskGraph) {
        HashMap<Integer, Double> criticalWays = new HashMap<>();
        HashMap<Integer, Integer> toTask = new HashMap<>();
        HashSet<Integer> endTasksID = new HashSet<>();
        for (Task task  : taskGraph.getTasks()) {
            endTasksID.add(task.getId());
        }
        for (TaskConnection connection : taskGraph.getTaskConnections()) {
            endTasksID.remove(connection.getFromTaskId());
        }

        for (Integer taskID  : endTasksID) {
            traverseTaskInDepthsBy2(taskGraph, taskGraph.getTask(taskID), -1, criticalWays, toTask, 0);
        }

        Map<Integer, Double> sortdetMap = sortByValue(criticalWays, (o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return 1;
            } else if (o1.getValue() < o2.getValue()) {
                return -1;
            } else {
                return (int) (taskGraph.getTask(o2.getKey()).getWeight() - taskGraph.getTask(o1.getKey()).getWeight());
            }
        });

        if (sortdetMap.size() != taskGraph.getTasks().size()) {
            System.out.println("getQueue5 broken");
        }


        int[] res = new int[sortdetMap.size()];
        final int[] i = {0};

        Map.Entry<Integer, Double> o = (Map.Entry<Integer, Double>) sortdetMap.entrySet().toArray()[res.length - 1];
        Integer taskID = o.getKey();
        while (taskID >= 0) {
            res[i[0] ++] = taskID;
            taskID = toTask.get(taskID);
        }
        i[0] = res.length - 1;
        sortdetMap.forEach((k, v) -> {if (!contains(res, k)) {
            if (i[0] < res.length)
             res[i[0] --] = k;
        }});

        return res;
    }

    private static  boolean contains(int[] a, int e) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == e) return true;
        }
        return false;
    }



    private static int[] getQueue5(TaskGraph taskGraph) {
        HashMap<Integer, Double> criticalWays = new HashMap<>();
        HashSet<Integer> endTasksID = new HashSet<>();
        for (Task task  : taskGraph.getTasks()) {
            endTasksID.add(task.getId());
        }
        for (TaskConnection connection : taskGraph.getTaskConnections()) {
            endTasksID.remove(connection.getFromTaskId());
        }

        for (Integer taskID  : endTasksID) {
                traverseTaskInDepths(taskGraph, taskGraph.getTask(taskID), criticalWays, 0);
        }

        Map<Integer, Double> sortdetMap = sortByValue(criticalWays, (o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return 1;
            } else if (o1.getValue() < o2.getValue()) {
                return -1;
            } else {
                return (int) (taskGraph.getTask(o2.getKey()).getWeight() - taskGraph.getTask(o1.getKey()).getWeight());
            }
        });

        if (sortdetMap.size() != taskGraph.getTasks().size()) {
            System.out.println("getQueue5 broken");
        }

        int[] res = new int[sortdetMap.size()];
        final int[] i = {res.length - 1};

        sortdetMap.forEach((k, v) -> res[i[0]--] = k);

        return res;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        list.sort(comparator);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }


    private static void traverseTaskInDepthsBy2(TaskGraph taskGraph, Task task, int toTaskID, HashMap<Integer, Double> graphCounter, HashMap<Integer, Integer> toTask, double level) {
        Double currentCritycal = graphCounter.get(task.getId());
        if (currentCritycal == null || currentCritycal < level) {
            currentCritycal = level;
            graphCounter.put(task.getId(), currentCritycal);
            toTask.put(task.getId(), toTaskID);
        }

        for (Integer id : task.getDependsFrom()) {
            TaskConnection taskConnection = taskGraph.getTaskConnection(id, task.getId());
            if (taskConnection != null) {
                traverseTaskInDepthsBy2(taskGraph, taskGraph.getTask(id), task.getId(), graphCounter, toTask, currentCritycal + 1);
            }
        }
    }


    private static void traverseTaskInDepths(TaskGraph taskGraph, Task task, HashMap<Integer, Double> graphCounter, double level) {
        Double currentCritycal = graphCounter.get(task.getId());
        if (currentCritycal == null || currentCritycal < level) {
            currentCritycal = level;
        }
        currentCritycal += task.getWeight();
        graphCounter.put(task.getId(), currentCritycal);

        for (Integer id : task.getDependsFrom()) {
            TaskConnection taskConnection = taskGraph.getTaskConnection(id, task.getId());
            if (taskConnection != null) {
                traverseTaskInDepths(taskGraph, taskGraph.getTask(id), graphCounter, currentCritycal);
            }
        }
    }

    private static int[] getQueue1(TaskGraph taskGraph) {
        List<TaskQueueData> taskQueueDatas = new ArrayList<>();
        int[][] adjacencyMatrix = taskGraph.getAdjacencyMatrix();
        int[][] transposedMatrix = transposeMatrix(adjacencyMatrix);
        //to end times
        timeToEndWays = new HashMap<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            timeToEndWays.put(i, getCriticalWayToEndByTime(i, 0, adjacencyMatrix));
        }
        //from start times
        timeFromStartWays = new HashMap<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            timeFromStartWays.put(i, getCriticalWayFromStartByTime(i, 0, transposedMatrix));
        }
        //graph critical time
        double graphCriticalTime = 0.0;
        for (Double aDouble : timeToEndWays.values()) {
            if (aDouble > graphCriticalTime) graphCriticalTime = aDouble;
        }
        // Pri
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            taskQueueDatas.add(new TaskQueueData(i, Math.abs(timeFromStartWays.get(i) - (graphCriticalTime - timeToEndWays.get(i)))));
//            System.out.println("t" + i + "(" + timeFromStartWays.get(i) + "-(" + graphCriticalTime + "-" + timeToEndWays.get(i) + ")");
        }
        //////
        taskQueueDatas.sort(new TaskQueue1Comparator());
        int[] queue = new int[adjacencyMatrix.length];
        for (int i = 0; i < taskQueueDatas.size(); i++) {
            queue[i] = taskGraph.getTaskIdByPos(taskQueueDatas.get(i).getId());
        }
        //print
//        System.out.println("Queue 1");
//        for (int i = 0; i < taskQueueDatas.size(); i++) {
//            int pos = 0;
//            for (Integer integer : timeFromStartWays.keySet()) {
//                if (integer == taskQueueDatas.get(i).getId()) {
//                    pos = integer;
//                    break;
//                }
//            }
//
//            System.out.println("t" + taskGraph.getTaskIdByPos(taskQueueDatas.get(i).getId()) + "(" +
//                    taskQueueDatas.get(i).getDeltaTime() + ")" + "   |(" + timeFromStartWays.get(pos) +
//                    "-(" + graphCriticalTime + "-" + timeToEndWays.get(pos) + ")|");
//        }
//        System.out.println();
        return queue;
    }

    private static int[] getQueue2(TaskGraph taskGraph) {
        List<TaskQueueData> taskQueueDatas = new ArrayList<>();
        int[][] adjacencyMatrix = taskGraph.getAdjacencyMatrix();
        //id, critical way
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            TaskQueueData taskQueueData = new TaskQueueData(i, getCriticalWayToEnd(i, 1, adjacencyMatrix));
            taskQueueDatas.add(taskQueueData);
        }
        //connectivity
        for (int i = 0; i < taskQueueDatas.size(); i++) {
            int connectivity = 0;
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 1 || adjacencyMatrix[j][i] == 1) connectivity++;
            }
            taskQueueDatas.get(i).setConnectivity(connectivity);
        }
        taskQueueDatas.sort(new TaskQueue2Comparator());
        //sort vertexNumberWays
        int[] queue = new int[adjacencyMatrix.length];
        for (int i = 0; i < taskQueueDatas.size(); i++) {
            queue[i] = taskGraph.getTaskIdByPos(taskQueueDatas.get(i).getId());
        }
        //print
//        System.out.println("Queue 2");
//        for (int i = 0; i < taskQueueDatas.size(); i++) {
//            System.out.println("t" + taskGraph.getTaskIdByPos(taskQueueDatas.get(i).getId()) + "(" +
//                    taskQueueDatas.get(i).getConnectivity() + "," + taskQueueDatas.get(i).getCriticalVertexWay() + ")");
//        }
//        System.out.println();
        return queue;
    }

    private static int[] getQueue3_hm(TaskGraph taskGraph) {
        timeFromStartWays = new HashMap<>();
        int[][] adjacencyMatrix = taskGraph.getAdjacencyMatrix();
        int[][] transposedMatrix = transposeMatrix(adjacencyMatrix);
        //
//        for (int i = 0; i < adjacencyMatrix.length; i++) {
//            for (int j = 0; j < transposedMatrix[i].length; j++) {
//                System.out.print(transposedMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        //
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            timeFromStartWays.put(i, getCriticalWayFromStartByTime(i, 0, transposedMatrix));
        }
        timeFromStartWays = CollectionUtils.sortByValuesFromMinToMax(timeFromStartWays);
        //sort vertexNumberWays
        int[] queue = new int[adjacencyMatrix.length];
        int count = 0;
        for (Integer integer : timeFromStartWays.keySet()) {
            queue[count++] = taskGraph.getTaskIdByPos(integer);
        }
        //print
//        System.out.println("Queue 3");
//        for (Integer integer : timeFromStartWays.keySet()) {
//            System.out.println("t" + taskGraph.getTaskIdByPos(integer) + "(" + timeFromStartWays.get(integer) + ")");
//        }
//        System.out.println();
        return queue;
    }

    private static int[] getQueue4(TaskGraph taskGraph) {
        List<Task> tasks = new LinkedList<>(taskGraph.getTasks());

        Random r = new Random();

        int[] queue = new int[tasks.size()];
        int i = 0;

        while (!tasks.isEmpty()) {
            int newIndex = r.nextInt(tasks.size());
            queue[i++] = tasks.remove(newIndex).getId();
        }

        return queue;
    }

    private static int getCriticalWayToEnd(int from, int value, int[][] adjacencyMatrix) {
        boolean isEndVertex = true;
        List<Integer> criticalWaysVariants = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix[from].length; i++) {
            if (adjacencyMatrix[from][i] == 1) {
                isEndVertex = false;
                return getCriticalWayToEnd(i, ++value, adjacencyMatrix);
            }
        }
        if (isEndVertex) {
            return value;
        } else {
//            int max = 0;
//            for (Integer criticalWaysVariant : criticalWaysVariants) {
//
//            }
        }
        return 0;
    }

    private static double getCriticalWayFromStartByTime(int from, double value, int[][] adjacencyMatrix) {
        boolean isEndVertex = true;
        List<Double> criticalWaysVariants = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix[from].length; i++) {
            if (adjacencyMatrix[from][i] == 1) {
                isEndVertex = false;
//                value += taskGraph.getTaskWeightByPos(from);
                criticalWaysVariants.add(getCriticalWayFromStartByTime(i, value, adjacencyMatrix) + taskGraph.getTaskWeightByPos(i));
//                return getCriticalWayFromStartByTime(i, value, adjacencyMatrix) + taskGraph.getTaskWeightByPos(i);
            }
        }
        if (isEndVertex) {
            return 0;//taskGraph.getTaskWeightByPos(from);
        } else {
            double max = 0.0;
            for (Double criticalWaysVariant : criticalWaysVariants) {
                if (criticalWaysVariant > max) max = criticalWaysVariant;
            }
            return max;
        }
    }

    private static double getCriticalWayToEndByTime(int from, double value, int[][] adjacencyMatrix) {
        boolean isEndVertex = true;
        for (int i = 0; i < adjacencyMatrix[from].length; i++) {
            if (adjacencyMatrix[from][i] == 1) {
                isEndVertex = false;
                value += taskGraph.getTaskWeightByPos(from);
                return getCriticalWayToEndByTime(i, value, adjacencyMatrix);
            }
        }
        if (isEndVertex) {
            return value += taskGraph.getTaskWeightByPos(from);
        }
        return 0;
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int[][] transposeMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                transposeMatrix[j][i] = matrix[i][j];
        return transposeMatrix;
    }

    //    private static List<Integer> getReadyToExecuteTasks(int[][] adjacencyMatrix) {
//        List<Integer> readyToExecuteTasks = new ArrayList<>();
//        if (adjacencyMatrix.length == 0) return readyToExecuteTasks;
//        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
//            boolean ready = true;
//            for (int j = 0; j < adjacencyMatrix.length; j++) {
//                if (adjacencyMatrix[j][i] == 1) {
//                    ready = false;
//                    break;
//                }
//            }
//            if (ready) readyToExecuteTasks.add(i);
//        }
//        return readyToExecuteTasks;
//    }
    public static void printQueue(int[] queue, TaskQueueUtils.TaskQueueType taskQueueType) {
        System.out.println("Queue: " + taskQueueType);
        for (int i = 0; i < queue.length; i++) {
            System.out.print(queue[i] + (i == queue.length - 1 ? "" : ", "));
        }
        System.out.println();
    }

    public enum TaskQueueType {
        RANDOM_WAY_13, CRITICAL_WAY_FIRST_DECR_NUMBER_5, QUEIE_3_CRITICAL_DECRES_WAY;
    }

    public static double getCriticalWayFromStartByTime(TaskGraph taskGraph, int from, double value, int[][] adjacencyMatrix) {
        boolean isEndVertex = true;
        List<Double> criticalWaysVariants = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix[from].length; i++) {
            if (adjacencyMatrix[from][i] == 1) {
                isEndVertex = false;
//                value += taskGraph.getTaskWeightByPos(from);
                criticalWaysVariants.add(getCriticalWayFromStartByTime(i, value, adjacencyMatrix) + taskGraph.getTaskWeightByPos(i));
//                return getCriticalWayFromStartByTime(i, value, adjacencyMatrix) + taskGraph.getTaskWeightByPos(i);
            }
        }
        if (isEndVertex) {
            return 0;//taskGraph.getTaskWeightByPos(from);
        } else {
            double max = 0.0;
            for (Double criticalWaysVariant : criticalWaysVariants) {
                if (criticalWaysVariant > max) max = criticalWaysVariant;
            }
            return max;
        }
    }
}
