package gui;

import java.awt.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class GridBagLayoutConfigurator {
    public static void setGridBagParam(GridBagConstraints gridBagConstraints, int gridx, int gridy, int gridwidth, int weightx, int weighty) {
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
    }
}
