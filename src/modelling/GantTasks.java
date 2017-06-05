package modelling;

import entities.computerSystem.ComputerSystem;
import entities.task.TaskGraph;
import modelling.entities.ResultModelling;
import utils.ModellingManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class GantTasks {
    private TaskGraph taskGraph;
    private ComputerSystem computerSystem;
    private List<GantViewEntity> tasksAndLinks;
    private ResultModelling resultModelling;

    public GantTasks(TaskGraph taskGraph, ComputerSystem computerSystem, int[] queue, AlgorithmType algorithmType) {
        this.taskGraph = taskGraph;
        this.computerSystem = computerSystem;
        createGangTasks(queue, algorithmType);
    }

    private void createGangTasks(int[] queue, AlgorithmType algorithmType) {
        this.tasksAndLinks = new ArrayList<>();
        switch (algorithmType) {
            case FIRST_FREE_PROCESSOR_2:
                resultModelling = ModellingManager.createResultModelling1(taskGraph, computerSystem, queue);
                break;
            case NEIGHBOUR_WITHOUT_NOTIFICATION_3:
                resultModelling = ModellingManager.createResultModelling2(taskGraph, computerSystem, queue);
                break;
        }
        tasksAndLinks = resultModelling.getGantView();
//        createTestGangTasks();
    }

//    private void createTestGangTasks(int[] queue) {
//        this.tasksAndLinks = new ArrayList<>();
//
//        GantViewEntity gantViewEntity1 = new GantViewEntity(taskGraph.getTask(0), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        gantViewEntity1.addStateStep("1");
//        tasksAndLinks.add(gantViewEntity1);
//
//        GantViewEntity gantViewEntity1L1 = new GantViewEntity(taskGraph.getTask(0), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity1L1.addStateStep("1-2", 4);
//        gantViewEntity1L1.addStateStep("1-2");
//        tasksAndLinks.add(gantViewEntity1L1);
//        //--------
//        GantViewEntity gantViewEntity2 = new GantViewEntity(taskGraph.getTask(1), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        tasksAndLinks.add(gantViewEntity2);
//
//        GantViewEntity gantViewEntity2L1 = new GantViewEntity(taskGraph.getTask(1), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity2L1.addStateStep("1-2", 4);
//        gantViewEntity2L1.addStateStep("1-2");
//        gantViewEntity2L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantViewEntity2L1);
//        //--------
//
//        GantViewEntity gantViewEntity3 = new GantViewEntity(taskGraph.getTask(2), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity3.addStateStep("2", 7);
//        gantViewEntity3.addStateStep("2");
//        tasksAndLinks.add(gantViewEntity3);
//
//        GantViewEntity gantViewEntity3L1 = new GantViewEntity(taskGraph.getTask(2), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity3L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantViewEntity3L1);
//        //--------
////        print();
//        //
//    }

//    private void createGangTasks1(int[] queue) {
//    }
//    private void createGangTasks1(int[] queue) {
//        this.tasksAndLinks = new ArrayList<>();
//
//        GantEntity gantEntity1 = new GantEntity(taskGraph.getTask(0), GantEntity.GantEntityType.PROCESSOR);
//        gantEntity1.addStateStep("1");
//        gantEntity1.addStateStep("1");
//        gantEntity1.addStateStep("1");
//        gantEntity1.addStateStep("1");
//        tasksAndLinks.add(gantEntity1);
//
//        GantEntity gantEntity1L1 = new GantEntity(taskGraph.getTask(0), GantEntity.GantEntityType.CONNECTION, 1);
//        gantEntity1L1.addStateStep("1-2", 4);
//        gantEntity1L1.addStateStep("1-2");
//        tasksAndLinks.add(gantEntity1L1);
//        //--------
//        GantEntity gantEntity2 = new GantEntity(taskGraph.getTask(1), GantEntity.GantEntityType.PROCESSOR);
//        gantEntity2.addStateStep("3");
//        gantEntity2.addStateStep("3");
//        gantEntity2.addStateStep("3");
//        gantEntity2.addStateStep("3");
//        tasksAndLinks.add(gantEntity2);
//
//        GantEntity gantEntity2L1 = new GantEntity(taskGraph.getTask(1), GantEntity.GantEntityType.CONNECTION, 1);
//        gantEntity2L1.addStateStep("1-2", 4);
//        gantEntity2L1.addStateStep("1-2");
//        gantEntity2L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantEntity2L1);
//        //--------
//
//        GantEntity gantEntity3 = new GantEntity(taskGraph.getTask(2), GantEntity.GantEntityType.PROCESSOR);
//        gantEntity3.addStateStep("2", 7);
//        gantEntity3.addStateStep("2");
//        tasksAndLinks.add(gantEntity3);
//
//        GantEntity gantEntity3L1 = new GantEntity(taskGraph.getTask(2), GantEntity.GantEntityType.CONNECTION, 1);
//        gantEntity3L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantEntity3L1);
//        //--------
////        print();
//        //
//    }

//    private void createGangTasks2(int[] queue) {
//        this.tasksAndLinks = new ArrayList<>();
//
//        GantViewEntity gantViewEntity1 = new GantViewEntity(taskGraph.getTask(0), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity1.addStateStep("4");
//        gantViewEntity1.addStateStep("4");
//        gantViewEntity1.addStateStep("41");
//        tasksAndLinks.add(gantViewEntity1);
//
//        GantViewEntity gantViewEntity1L1 = new GantViewEntity(taskGraph.getTask(0), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity1L1.addStateStep("1-2", 3);
//        gantViewEntity1L1.addStateStep("1-2");
//        tasksAndLinks.add(gantViewEntity1L1);
//        //--------
//        GantViewEntity gantViewEntity2 = new GantViewEntity(taskGraph.getTask(1), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        gantViewEntity2.addStateStep("3");
//        tasksAndLinks.add(gantViewEntity2);
//
//        GantViewEntity gantViewEntity2L1 = new GantViewEntity(taskGraph.getTask(1), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity2L1.addStateStep("1-2", 3);
//        gantViewEntity2L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantViewEntity2L1);
//        //--------
//
//        GantViewEntity gantViewEntity3 = new GantViewEntity(taskGraph.getTask(2), GantViewEntity.GantEntityType.PROCESSOR);
//        gantViewEntity3.addStateStep("2", 7);
//        tasksAndLinks.add(gantViewEntity3);
//
//        GantViewEntity gantViewEntity3L1 = new GantViewEntity(taskGraph.getTask(2), GantViewEntity.GantEntityType.CONNECTION, 1);
//        gantViewEntity3L1.addStateStep("2-3", 6);
//        tasksAndLinks.add(gantViewEntity3L1);
//        //--------
////        print();
//        //
//    }

    public int getTotalNumberOfPEAndRealConnections() {
        return tasksAndLinks.size();
    }

    public int numberOfSteps() {
        int max = 0;
        for (GantViewEntity tasksAndLink : tasksAndLinks) {
            if (tasksAndLink.getState().size() > max)
                max = tasksAndLink.getState().size();
        }
        return max;
    }

    public String getEntityName(int i) {
        return tasksAndLinks.get(i).getName();
    }

    public List<GantViewEntity> getTasksAndLinks() {
        return tasksAndLinks;
    }

    public void print() {
        for (GantViewEntity tasksAndLink : tasksAndLinks) {
            System.out.println(tasksAndLink.getName());
            for (String s : tasksAndLink.getState()) {
                System.out.print(s + ",");
            }
            System.out.println();
        }
    }
}
