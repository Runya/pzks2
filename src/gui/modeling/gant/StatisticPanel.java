package gui.modeling.gant;

import javax.swing.*;

/**
 * Created by hadgehog on 18.05.2014.
 */
public class StatisticPanel extends JPanel {
    private final JLabel kyLabel;
    private final JLabel keLabel;

    public StatisticPanel(double ky, double ke) {
        kyLabel = new JLabel();
        keLabel = new JLabel();
        add(kyLabel);
        add(keLabel);
        setData(ky, ke);
    }

    public void setData(double ky, double ke) {
        kyLabel.setText("Ky = " + ky + "        ");
        keLabel.setText("Ke = " + ke);
    }
}
