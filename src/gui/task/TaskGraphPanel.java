package gui.task;

import gui.task.figures.TaskConnectionLine;
import gui.task.figures.TaskOval;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class TaskGraphPanel extends JPanel {

    public boolean drawTempConnectionLine = false;
    public int tempLineX1;
    public int tempLineY1;
    public int tempLineX2;
    public int tempLineY2;

    private final int taskOvalRadius = 30;

    //	rgb(0,191,255)
    private final Color taskOvalsFillColor = new Color(0, 191, 255);
    private final Color taskOvalsColor = new Color(0, 191, 255);
    private final Color taskOvalsTextColor = new Color(0, 0, 0);
    public static final Color connectiveLineColor = new Color(22, 87, 3);
    public static final Color connectiveLineNameColor = new Color(129, 65, 23);
    private final Color connectiveDirectLineColor = new Color(200, 0, 23);

    private List<TaskOval> taskOvals = new ArrayList();
    private List<TaskConnectionLine> taskConnectionLines = new ArrayList<>();

    private double connectivity = 0.0;

    public TaskGraphPanel() {
        setLayoutParameters();
    }

    private void setLayoutParameters() {
        setLayout(new BorderLayout());
    }

    public void addTaskToDraw(int id, double weight, int x, int y) {
        TaskOval taskOval = new TaskOval(id, weight, x, y, taskOvalRadius, taskOvalsColor, taskOvalsFillColor);
        taskOvals.add(taskOval);
    }

    public void removeTaskToDraw(int id) {
        TaskOval taskOval = getTaskOval(id);
        taskOvals.remove(taskOval);
    }

    public void addConnectionToDraw(int taskFromId, int taskToId, double bandwidth) {
        TaskOval taskFromOval = getTaskOval(taskFromId);
        TaskOval taskToOval = getTaskOval(taskToId);
        TaskConnectionLine taskConnectionLine = new TaskConnectionLine(taskFromOval, taskToOval, taskOvalRadius, bandwidth);
        taskConnectionLines.add(taskConnectionLine);
    }


    public void editTaskToDraw(int id, double weight, int x, int y) {
        TaskOval taskOval = getTaskOval(id);
        taskOval.setWeight(weight);
        taskOval.setX(x);
        taskOval.setY(y);
    }

    public void paint(Graphics g) {
        //draw task ovals
        drawConnections(g);
        if (drawTempConnectionLine)
            drawTempConnectionLine(g);
        drawTasks(g);
        drawConnectivity(g);
    }

    private void drawTasks(Graphics g) {
        for (TaskOval taskOval : taskOvals) {
//            System.out.println("Task: id=" + taskOval.getId() + ", weight=" + taskOval.getPower() + ", x=" + taskOval.getX() + ", y=" + taskOval.getY());
            g.setColor(taskOval.getFillColor());
            g.fillOval(taskOval.getX(), taskOval.getY(), taskOvalRadius, taskOvalRadius);
            g.setColor(taskOval.getColor());
            g.drawOval(taskOval.getX(), taskOval.getY(), taskOvalRadius, taskOvalRadius);
            g.setColor(taskOvalsTextColor);
            g.drawString("t" + taskOval.getId(), taskOval.getX() + taskOvalRadius / 2, taskOval.getY() + taskOvalRadius / 2);
            g.drawString("w" + taskOval.getWeight(), taskOval.getX() + (int) (1.1 * taskOvalRadius), taskOval.getY() + taskOvalRadius / 2);
        }
    }

    private void drawConnections(Graphics g) {
        for (TaskConnectionLine taskConnectionLine : taskConnectionLines) {
//            System.out.println("Task connection: taskFromId=" + taskConnectionLine.getTaskFromOval().getId() +
//                    "taskToId=" + taskConnectionLine.getTaskToOval().getId());
            g.setColor(connectiveLineColor);
            g.drawLine(taskConnectionLine.getX1(), taskConnectionLine.getY1(), taskConnectionLine.getX2(), taskConnectionLine.getY2());
            //draw arrow http://habrahabr.ru/post/105882/
            drawArrow(taskConnectionLine, g);

            g.setColor(connectiveLineNameColor);
            g.drawString(taskConnectionLine.getName(), taskConnectionLine.getNameX(), taskConnectionLine.getNameY());
        }
    }

    private void drawConnectivity(Graphics g) {
        int y = this.getHeight();
        g.drawString("Звязність = " + connectivity, 10, y);
    }

    private void drawTempConnectionLine(Graphics g) {
        g.drawLine(tempLineX1, tempLineY1, tempLineX2, tempLineY2);
    }

    private void drawArrow(TaskConnectionLine taskConnectionLine, Graphics g) {
        //БИДЛОКОД!!!
        int r = taskOvalRadius / 2;
        int xx2 = taskConnectionLine.getX1();
        int yy2 = taskConnectionLine.getY1();
        int xx1 = taskConnectionLine.getX2();
        int yy1 = taskConnectionLine.getY2();
        int xo = (int) (xx1 + r * (xx2 - xx1) / Math.sqrt(Math.pow((xx2 - xx1), 2) + Math.pow((yy2 - yy1), 2)));
        int yo = (int) (yy1 + r * (yy2 - yy1) / Math.sqrt(Math.pow((xx2 - xx1), 2) + Math.pow((yy2 - yy1), 2)));
        //
        int tipX = xo;//taskConnectionLine.getX2();
        int tipY = yo;//taskConnectionLine.getY2();
        int tailX = taskConnectionLine.getX1();
        int tailY = taskConnectionLine.getY1();
        int arrowLength = 15; //can be adjusted
        int dx = tipX - tailX;
        int dy = tipY - tailY;

        double theta = Math.atan2(dy, dx);

        double rad = Math.toRadians(35); //35 angle, can be adjusted
        double x = tipX - arrowLength * Math.cos(theta + rad);
        double y = tipY - arrowLength * Math.sin(theta + rad);

        double phi2 = Math.toRadians(-35);//-35 angle, can be adjusted
        double x2 = tipX - arrowLength * Math.cos(theta + phi2);
        double y2 = tipY - arrowLength * Math.sin(theta + phi2);

        int[] arrowYs = new int[3];
        arrowYs[0] = tipY;
        arrowYs[1] = (int) y;
        arrowYs[2] = (int) y2;

        int[] arrowXs = new int[3];
        arrowXs[0] = tipX;
        arrowXs[1] = (int) x;
        arrowXs[2] = (int) x2;

        g.fillPolygon(arrowXs, arrowYs, 3);
    }

    public TaskOval getTaskOval(int id) {
        for (TaskOval taskOval : taskOvals) {
            if (taskOval.getId() == id) {
                return taskOval;
            }
        }
        return null;
    }

    public void removeAllTasks() {
        taskOvals = new ArrayList<>();
        taskConnectionLines = new ArrayList<>();
    }

    public void removeAllConnections() {
        taskConnectionLines = new ArrayList<>();
    }

    public List<TaskOval> getTaskOvals() {
        return taskOvals;
    }

    public List<TaskConnectionLine> getTaskConnectionLines() {
        return taskConnectionLines;
    }

    public void setNewCoordinate(int id, int x, int y) {
        TaskOval taskOval = getTaskOval(id);
        taskOval.setX(x);
        taskOval.setY(y);

        for (TaskConnectionLine taskConnectionLine : taskConnectionLines) {
            if (taskConnectionLine.getTaskFromOval().getId() == id) {
                taskConnectionLine.setX1(x + taskOvalRadius / 2);
                taskConnectionLine.setY1(y + taskOvalRadius / 2);
            }
            if (taskConnectionLine.getTaskToOval().getId() == id) {
                taskConnectionLine.setX2(x + taskOvalRadius / 2);
                taskConnectionLine.setY2(y + taskOvalRadius / 2);
            }
        }
    }

    public int getTaskOvalRadius() {
        return taskOvalRadius;
    }

    public void setNewWeight(int id, double newWeight) {
        getTaskOval(id).setWeight(newWeight);
    }

    public double getTaskConnectionBandwidth(int fromID, int toId) {
        return getTaskConnectionLine(fromID, toId).getBandwidth();
    }

    public TaskConnectionLine getTaskConnectionLine(int fromId, int toId) {
        for (TaskConnectionLine taskConnectionLine : taskConnectionLines) {
            if (taskConnectionLine.getTaskFromOval().getId() == fromId & taskConnectionLine.getTaskToOval().getId() == toId) {
                return taskConnectionLine;
            }
        }
        return null;
    }

    public void setNewBandwidth(int fromId, int toId, double newBandwidth) {
        getTaskConnectionLine(fromId, toId).setBandwidth(newBandwidth);
    }

    public void setConnectivity(double connectivity) {
        this.connectivity = connectivity;
    }
}
