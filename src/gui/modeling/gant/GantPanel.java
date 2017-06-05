package gui.modeling.gant;

import modelling.GantTasks;
import modelling.GantViewEntity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class GantPanel extends JPanel {
    public final int DEFAULT_CELL_WIDTH = 120;
    public final int DEFAULT_CELL_HEIGTH = 35;
    private final GantGraphFrame gantGraphFrame;
    private int panelHeight;
    private int panelWidth;
    private int cellHeight;
    private int cellWidth;
    private int numbOfRows;
    private int numberOfColumns;
    private GantTasks gantTasks;

    public GantPanel(GantGraphFrame gantGraphFrame, GantTasks gantTasks) {
        this.gantGraphFrame = gantGraphFrame;
        this.gantTasks = gantTasks;
    }

    public void setGantTasks(GantTasks gantTasks) {
        this.gantTasks = gantTasks;
    }

    public GantTasks getGantTasks() {
        return gantTasks;
    }

    @Override
    public void paint(Graphics g) {
        defineSizeParameters();
        drawCells(g);
        drawLattice(g);
        drawXAxis(g);
        drawYAxis(g);
    }

    private void drawLattice(Graphics g) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < numbOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (j == 0 || i == numbOfRows - 1) {
                    g.setColor(GantPanelColors.axisFillColor);
                    g.fillRect(x, y, cellWidth, cellHeight);
                }
                g.setColor(GantPanelColors.latticeColor);
                g.drawRect(x, y, cellWidth, cellHeight);
                x += cellWidth;
            }
            x = 0;
            y += cellHeight;
        }
    }

    private void drawXAxis(Graphics g) {
        int x = cellWidth * 3 / 2;
        int y = panelHeight - cellHeight / 2;
        for (int i = 0; i < numberOfColumns - 1; i++) {
            g.drawString("" + (i + 1), x, y);
            x += cellWidth;
        }
    }

    private void drawYAxis(Graphics g) {
        int x = cellWidth / 5;
        int y = cellHeight / 2;
        for (int i = 0; i < numbOfRows - 1; i++) {
            g.drawString("" + gantTasks.getEntityName(i), x, y);
            y += cellHeight;
        }
    }

    private void drawCells(Graphics g) {
        List<GantViewEntity> entityList = gantTasks.getTasksAndLinks();
        for (int i = 0; i < entityList.size(); i++) {
//            int y = (cellHeight / 2) + i * cellHeight;
            int y = (cellHeight / 2) + 5 + i * cellHeight;
            for (int j = 0; j < numberOfColumns - 1; j++) {
//                int x = cellWidth + (cellWidth / 2) + j * cellWidth;
                int x = cellWidth + (2) + j * cellWidth;
                String text = entityList.get(i).getStep(j);
                Color fillColor = text.equals(GantViewEntity.SIMPLE_WORK_STATE) ? GantPanelColors.taskSimpleColor : GantPanelColors.taskWorkColor;
                fillCell(x, y, text, fillColor, g);
            }
        }
    }

    private void fillCell(int cellX, int cellY, String text, Color color, Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(text, cellX, cellY);
    }

    private void defineSizeParameters() {
        numbOfRows = gantTasks.getTotalNumberOfPEAndRealConnections() + 1;//+timeline
        numberOfColumns = gantTasks.numberOfSteps() + 1;//+PE and connections
        changeFrameSize(numbOfRows, numberOfColumns);
        panelHeight = getHeight();
        panelWidth = getWidth();
        cellHeight = panelHeight / numbOfRows;
        cellWidth = panelWidth / numberOfColumns;
    }

    private void changeFrameSize(int numbOfRows, int numberOfColumns) {
        int frameWidth = numberOfColumns * DEFAULT_CELL_WIDTH;
        int frameHeight = numbOfRows * DEFAULT_CELL_HEIGTH;
        frameWidth = frameWidth < gantGraphFrame.MIN_WIDTH ? gantGraphFrame.MIN_WIDTH : frameWidth;
        frameHeight = frameHeight < gantGraphFrame.MIN_HEIGHT ? gantGraphFrame.MIN_HEIGHT : frameHeight;
        frameWidth = frameWidth > gantGraphFrame.MAX_WIDTH ? gantGraphFrame.MAX_WIDTH : frameWidth;
        frameHeight = frameHeight > gantGraphFrame.MAX_HEIGHT ? gantGraphFrame.MAX_HEIGHT : frameHeight;
        gantGraphFrame.setSize(frameWidth, frameHeight);
    }
}
