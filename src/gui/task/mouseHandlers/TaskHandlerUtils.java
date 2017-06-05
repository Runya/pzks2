package gui.task.mouseHandlers;

import gui.task.TaskGraphPanel;
import gui.task.figures.TaskConnectionLine;
import gui.task.figures.TaskOval;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskHandlerUtils {

    public static int defineVertexId(int x, int y, TaskGraphPanel taskGraphPanel) {
        //search vertex
        for (TaskOval taskOval : taskGraphPanel.getTaskOvals()) {
            if (taskOvalContainPoint(taskOval, x, y)) {
                return taskOval.getId();
            }
        }
        return -1;
    }

    private static boolean taskOvalContainPoint(TaskOval taskOval, int x, int y) {
        int x1 = taskOval.getX();
        int y1 = taskOval.getY();
        int x2 = x1 + taskOval.getRadius();
        int y2 = y1 + taskOval.getRadius();
        return (x > x1 & x < x2 & y > y1 & y < y2);
    }

    public static int[] defineConnectionId(int x, int y, TaskGraphPanel taskGraphPanel) {
        //search connection
        for (TaskConnectionLine taskConnectionLine : taskGraphPanel.getTaskConnectionLines()) {
            int xc = (taskConnectionLine.getX1() + taskConnectionLine.getX2()) / 2;
            int yc = (taskConnectionLine.getY1() + taskConnectionLine.getY2()) / 2;
            int x1 = xc - 30;
            int x2 = xc + 30;
            int y1 = yc - 30;
            int y2 = yc + 30;
            if (x > x1 & x < x2 & y > y1 & y < y2) {
                return new int[]{taskConnectionLine.getTaskFromOval().getId(), taskConnectionLine.getTaskToOval().getId()};
            }
        }
        return new int[]{-1, -1};
    }
}
