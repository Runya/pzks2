package gui.system.mouseHandlers;

import gui.system.SystemGraphPanel;
import gui.system.figures.ProcessorConnectionLine;
import gui.system.figures.ProcessorSquare;

/**
 * Created by hadgehog on 18.02.14.
 */
public class SystemHandlerUtils {

    public static int defineVertexId(int x, int y, SystemGraphPanel systemGraphPanel) {
        //search vertex
        for (ProcessorSquare processorSquare : systemGraphPanel.getProcessorSquares()) {
            if (processorSquareContainPoint(processorSquare, x, y)) {
                return processorSquare.getId();
            }
        }
        return -1;
    }

    private static boolean processorSquareContainPoint(ProcessorSquare processorSquare, int x, int y) {
        int x1 = processorSquare.getX();
        int y1 = processorSquare.getY();
        int x2 = x1 + processorSquare.getRadius();
        int y2 = y1 + processorSquare.getRadius();
        return (x > x1 & x < x2 & y > y1 & y < y2);
    }

    public static int[] defineConnectionId(int x, int y, SystemGraphPanel systemGraphPanel) {
        //search connection
        for (ProcessorConnectionLine processorConnectionLine : systemGraphPanel.getProcessorConnectionLines()) {
            int xc = (processorConnectionLine.getX1() + processorConnectionLine.getX2()) / 2;
            int yc = (processorConnectionLine.getY1() + processorConnectionLine.getY2()) / 2;
            int x1 = xc - 30;
            int x2 = xc + 30;
            int y1 = yc - 30;
            int y2 = yc + 30;
            if (x > x1 & x < x2 & y > y1 & y < y2) {
                return new int[]{processorConnectionLine.getTaskFromOval().getId(), processorConnectionLine.getTaskToOval().getId()};
            }
        }
        return new int[]{-1, -1};
    }
}
