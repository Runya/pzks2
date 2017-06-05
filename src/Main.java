import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;
import utils.WorkManager;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        run();
//        testTaskGui();
//        testSystemGui();
//        testModellingFrame();
    }

    private static void run() {
        WorkManager workManager = new WorkManager();
    }


    private static void testSystemGui() throws InterruptedException {
        TaskGraph taskGraph = new TaskGraph();
        taskGraph.addTask(200, 50);//0
        taskGraph.addTask(400, 50);//1
        taskGraph.addTask(100, 150);//2

        ComputerSystem computerSystem = new ComputerSystem();
        computerSystem.addProcessor(100, 100);
        computerSystem.addProcessor(200, 200);

        WorkManager workManager = new WorkManager(taskGraph, computerSystem);

        Thread.sleep(1000);
        workManager.editProcessor(0, 300, 300);
    }

    private static void testTaskGui() {
        TaskGraph taskGraph = new TaskGraph();
        taskGraph.addTask(200, 50);//0
        taskGraph.addTask(400, 50);//1
        taskGraph.addTask(100, 150);//2
//        taskGraph.addTask(60, 300);//3

        taskGraph.addDepend(1, 0);
        taskGraph.addDepend(2, 1);
//        taskGraph.addDepend(3, 0);
//        taskGraph.addDepend(3, 2);

        ComputerSystem computerSystem = null;
        WorkManager workManager = new WorkManager(taskGraph, computerSystem);

        try {
            Thread.sleep(1000);
            Thread.sleep(1000);
//            workManager.addConnection(4, 2);
//
//            Thread.sleep(1000);
//            workManager.removeTask(1);
//            Thread.sleep(1000);
//            workManager.removeConnection(2, 3);
//
//            Thread.sleep(1000);
//            workManager.editTask(2,3,300,200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void testModellingFrame() {
        TaskGraph taskGraph = new TaskGraph();
        taskGraph.addTask(200, 50);//0
        taskGraph.setNewWeight(0, 5);
        taskGraph.addTask(300, 150);//1
        taskGraph.addTask(400, 50);//2
        taskGraph.addTask(300, 300);//3
        taskGraph.addDepend(1, 0);
        taskGraph.addDepend(1, 2);
        taskGraph.addDepend(3, 1);


        ComputerSystem computerSystem = new ComputerSystem();
        computerSystem.addProcessor(250, 100);//0
        computerSystem.setNewPower(0, 2);
        computerSystem.addProcessor(200, 200);//1
        computerSystem.addDepend(0, 1);
        computerSystem.addProcessor(300, 200);//2
        computerSystem.addDepend(0, 2);
        computerSystem.addProcessor(250, 300);//3
        computerSystem.addDepend(1, 3);
        computerSystem.addDepend(2, 3);
        computerSystem.setNewBandwidth(0, 1, 2);
        computerSystem.setNewBandwidth(3, 2, 3);
        int from = 0;
        int to = 3;
        System.out.println(" min(" + from + "," + to + ")  = " + computerSystem.getMinWayValue(from, to));
        int[] minWay = computerSystem.getMinWay(from, to);
        WorkManager workManager = new WorkManager(taskGraph, computerSystem);
//        new GantGraphFrame(workManager);
    }
}
