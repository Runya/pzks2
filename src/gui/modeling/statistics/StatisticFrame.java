package gui.modeling.statistics;

import gui.GridBagLayoutConfigurator;
import utils.WorkManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hadgehog on 19.04.2014.
 */
public class StatisticFrame extends JFrame {
    public final int MIN_WIDTH = 700;
    public final int MIN_HEIGHT = 400;
    public int MAX_WIDTH;
    public int MAX_HEIGHT;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private WorkManager workManager;
    private JPanel resultPanel;
    private JTextArea resultsTextArea;

    public StatisticFrame(WorkManager workManager) throws HeadlessException {
        this.workManager = workManager;
        setSize_Position();
        setLayoutParameters();
        createPanels(workManager);
        addPanels();

        setVisible(true);
    }

    public StatisticFrame(WorkManager workManager, String result) throws HeadlessException {
        this(workManager);
        resultsTextArea.setText(result);
    }

    private void createPanels(WorkManager workManager) {
        resultPanel = new JPanel();
        resultsTextArea = new JTextArea();
        resultsTextArea.setFont(new Font("sansserif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);//TODO
        scrollPane.setAutoscrolls(true);
        resultPanel.add(scrollPane);
    }

    private void addPanels() {
        GridBagLayoutConfigurator.setGridBagParam(gridBagConstraints, 0, 0, 1, 1, 1);
        add(resultPanel, gridBagConstraints);
    }

    private void setSize_Position() {
//        setResizable(false);
        setTitle("Статистика");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        MAX_WIDTH = screenSize.width;
        MAX_HEIGHT = screenSize.height;
//        int frameWidth = screenSize.width * 7 / 8;
//        int frameHeight = screenSize.height * 7 / 8;
//        frameWidth /= 2;//remove for one monitor
        int frameWidth = MIN_WIDTH;
        int frameHeight = MIN_HEIGHT;
        int leftCornerH = (screenSize.height - frameHeight) / 2;
//        int leftCornerW = (screenSize.width - frameWidth) / 2; //for one monitor
        int leftCornerW = 40;
        setBounds(leftCornerW, leftCornerH, frameWidth, frameHeight);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setLayoutParameters() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }
}
