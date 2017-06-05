package gui.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by hadgehog on 16.02.14.
 */
public class HelpMenu {
    private static  final String helpFile = "docs\\pzks2.help.chm";
    public static JMenu getMenu() {
        JMenu helpMenu = new JMenu("Допомога");
        JMenuItem help = new JMenuItem("Документація по програмі");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().open(new File(helpFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        JSeparator separator = new JSeparator();
        JMenuItem about = new JMenuItem("Про програму");
        helpMenu.add(help);
        helpMenu.add(separator);
        helpMenu.add(about);
        return helpMenu;
    }
}
