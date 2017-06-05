package gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by byte on 4/27/17.
 */
public class Utils_GUI {

    public JButton makeBarJButton(String iconSrc, String tooltip) {
        JButton newVertexButton = new JButton();
        Image scalableIcon = getScalableIcon(iconSrc);
        newVertexButton.setIcon(new ImageIcon(scalableIcon));
        newVertexButton.setToolTipText(tooltip);
        return newVertexButton;
    }

    public Image getScalableIcon(String resource) {
        ImageIcon newVertexIcon = (new ImageIcon(getClass().getResource(resource)));
        Image img = newVertexIcon.getImage();
        return img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    }

}
