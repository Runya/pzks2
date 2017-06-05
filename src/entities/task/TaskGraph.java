package entities.task;

import java.io.*;
import java.util.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class TaskGraph implements Serializable {

    private List<Task> tasks;
    private List<TaskConnection> taskConnections;
    private int[][] adjacencyMatrix;
    private Map<Integer, Integer> taskPosId = new HashMap<>();

    public TaskGraph() {
        tasks = new ArrayList<Task>();
        taskConnections = new ArrayList<>();
//        taskAdjacencyMatrix = new int[0][0];
    }

    public TaskGraph(List<Task> tasks, List<TaskConnection> taskConnections) {
        this.tasks = tasks;
        this.taskConnections = taskConnections;
    }

    public int addTask(int x, int y) {
        int id = defineNewId();
        Task task = new Task(id, x, y);
        tasks.add(task);
        createAdjacencyMatrix();
        return id;
    }

    public int addTask(double weight) {
        int id = defineNewId();
        Task task = new Task(id, weight);
        tasks.add(task);
        createAdjacencyMatrix();
        return id;
    }

    public double getTaskWeightById(int id) {
        return getTask(id).getWeight();
    }

    public int getTaskX(int id) {
        return getTask(id).getX();
    }

    public int getTaskY(int id) {
        return getTask(id).getY();
    }

    public void changeTaskX(int id, int newX) {
        getTask(id).setX(newX);
    }

    public void changeTaskY(int id, int newY) {
        getTask(id).setY(newY);
    }

    private int defineNewId() {
        int id = -1;
        boolean idExist = true;
        while (idExist) {
            id++;
            idExist = false;
            for (Task task : tasks) {
                if (task.getId() == id) {
                    idExist = true;
                    break;
                }
            }
        }
        return id;
    }

    public void readFromFile(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream oin = new ObjectInputStream(fis);
        TaskGraph taskGraph = (TaskGraph) oin.readObject();
        this.tasks = taskGraph.getTasks();
        this.taskConnections = taskGraph.getTaskConnections();
        createAdjacencyMatrix();
    }

    public void saveToFile(String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int[][] getTasksData() {
        int[][] tasksData = new int[tasks.size()][3];// [id, weight, x, y]
        for (int i = 0; i < tasksData.length; i++) {
            tasksData[i][0] = tasks.get(i).getId();
            tasksData[i][1] = tasks.get(i).getX();
            tasksData[i][2] = tasks.get(i).getY();
        }
        return tasksData;
    }

    public int[][] getConnectionsData() {
        List<int[]> connectionsDataList = new ArrayList<>();
        for (Task task : tasks) {
            Set<Integer> dependsFrom = task.getDependsFrom();
            for (Integer depend : dependsFrom) {
                int[] connection = {depend, task.getId()};
                connectionsDataList.add(connection);
            }
        }
        int[][] connectionDataArray = new int[connectionsDataList.size()][];
        for (int i = 0; i < connectionsDataList.size(); i++) {
            connectionDataArray[i] = connectionsDataList.get(i);
        }
        return connectionDataArray;
    }

    public int[] getTaskData(int id) {
        Task task = getTask(id);
        int[] taskData = new int[4];//// [id, weight, x, y]
        taskData[0] = task.getId();
        taskData[1] = task.getX();
        taskData[2] = task.getY();
        return taskData;
    }

    public void setTaskData(int id, int newWeight, int newX, int newY) {
        Task task = getTask(id);
        task.setWeight(newWeight);
        task.setX(newX);
        task.setY(newY);
    }

    public void setTaskData(int id, int newX, int newY) {
        Task task = getTask(id);
        task.setX(newX);
        task.setY(newY);
    }

    public boolean addDepend(int taskId, int dependsFrom) {
        //зв'язок "сам на себе"
        if (taskId == dependsFrom) {
            return false;
        }
        Task task = getTask(taskId);
        if (cycleConnection(taskId, dependsFrom))
            return false;
        task.addDependsFrom(dependsFrom);
        TaskConnection taskConnection = new TaskConnection(dependsFrom, taskId);
        taskConnections.add(taskConnection);
        createAdjacencyMatrix();
        return true;//true - додано, false - буде цикл
    }

    public boolean addDepend(int taskId, int dependsFrom, double bandwidth) {
        if (!addDepend(taskId, dependsFrom)) return false;
        getTaskConnection(dependsFrom, taskId).setBandwidth(bandwidth);
        return true;
    }

    public void addDependWeight(int fromId, int toId, double bandwidth) {
        getTaskConnection(fromId, toId).increaseBandwidth(bandwidth);
    }

    public boolean connectionExist(int fromId, int toId) {
        Task toTask = getTask(toId);
        Set<Integer> dependsFrom = toTask.getDependsFrom();
        return dependsFrom.contains(fromId);
    }

    //Перевірка на ациклічність
    private boolean cycleConnection(int taskId, int dependsFrom) {
        Task thisTask = getTask(dependsFrom);
        for (Integer dependFromID : thisTask.getDependsFrom()) {
            if (dependFromID == taskId) {
                return true;
            }
            if (cycleConnection(taskId, dependFromID))
                return true;
        }
        return false;
    }

    public void removeDepend(int dependsFrom, int taskId) {
        Task task = getTask(taskId);
        task.removeDependsFrom(dependsFrom);
        taskConnections.remove(getTaskConnection(dependsFrom, taskId));
        createAdjacencyMatrix();
    }

    public void removeTask(int id) {
        Task task = getTask(id);
        tasks.remove(task);
        for (Task task1 : tasks) {
            task1.getDependsFrom().remove(Integer.valueOf(id));
        }


        for (int i = 0; i < taskConnections.size(); i++) {
            TaskConnection taskConnection = taskConnections.get(i);
            if (taskConnection.getFromTaskId() == id | taskConnection.getToTaskId() == id) {
                taskConnections.remove(taskConnection);
            }
        }
        createAdjacencyMatrix();
    }

    public void removeAllTask() {
        tasks = new ArrayList<>();
        taskConnections = new ArrayList<>();
        createAdjacencyMatrix();
    }

    public void setNewWeight(int id, double newWeight) {
        getTask(id).setWeight(newWeight);
    }

    public TaskConnection getTaskConnection(int fromId, int toId) {
        for (TaskConnection taskConnection : taskConnections) {
            if (taskConnection.getFromTaskId() == fromId & taskConnection.getToTaskId() == toId) {
                return taskConnection;
            }
        }
        return null;
    }

    public double getConnectionBandwidth(int fromId, int toId) {
        return getTaskConnection(fromId, toId).getBandwidth();
    }

    public void setNewBandwidth(int fromId, int toId, double newBandwidth) {
        getTaskConnection(fromId, toId).setBandwidth(newBandwidth);
    }

    public List<TaskConnection> getTaskConnections() {
        return taskConnections;
    }

    private void createAdjacencyMatrix() {
        Map<Integer, Integer> taskIdPos = new HashMap<>();
        taskPosId = new HashMap<>();
        for (int i = 0; i < tasks.size(); i++) {
            taskIdPos.put(tasks.get(i).getId(), i);
            taskPosId.put(i, tasks.get(i).getId());
        }
        adjacencyMatrix = new int[tasks.size()][tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            Set<Integer> dependsFrom = tasks.get(i).getDependsFrom();
            for (Integer depend : dependsFrom) {
                adjacencyMatrix[taskIdPos.get(depend)][i] = 1;
            }
        }

//        for (int i = 0; i < adjacencyMatrix.length; i++) {
//            System.out.print(taskPosId.get(i) + ":");
//            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
//                System.out.print(adjacencyMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    public int[][] getAdjacencyMatrix() {
        createAdjacencyMatrix();
        return adjacencyMatrix == null ? new int[0][0] : adjacencyMatrix;
    }

    public double getTaskWeightByPos(int pos) {
        return getTask(taskPosId.get(pos)).getWeight();
    }

    public int getTaskIdByPos(int pos) {
        return taskPosId.get(pos);
    }

    public int getTaskPosById(int id) {
        for (Integer pos : taskPosId.keySet()) {
            if (taskPosId.get(pos) == id) return pos;
        }
        return -1;
    }

    public double getGraphConnectivity() {
        double taskWeights = 0.0;
        for (Task task : tasks) {
            taskWeights += task.getWeight();
        }
        double connectionWeights = 0.0;
        for (TaskConnection taskConnection : taskConnections) {
            connectionWeights += taskConnection.getBandwidth();
        }
        return (taskWeights + connectionWeights == 0) ? 0 : (taskWeights) / (taskWeights + connectionWeights);
    }

    public void incTaskWeight(int taskId) {
        Task task = getTask(taskId);
        task.setWeight(task.getWeight() + 1.0);
    }

    public void decTaskWeight(int taskId) {
        Task task = getTask(taskId);
        if (task.getWeight() > 1.0) task.setWeight(task.getWeight() - 1.0);
    }

    public int[] getFatherTasksIds(Task task) {
        List<Integer> ids = new ArrayList<>();
        for (TaskConnection connection : taskConnections) {
            if (connection.getToTaskId() == task.getId()) {
                ids.add(connection.getFromTaskId());
            }
        }
        int[] idsArray = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            idsArray[i] = ids.get(i);
        }
        return idsArray;
    }

    public double getCriticalWeightWay() {

        return 0.1;
    }
}
