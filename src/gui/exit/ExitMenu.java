package gui.exit;

import gui.exit.listeners.HardExitProgramListener;
import gui.exit.listeners.SaveExitProgramListener;

import javax.swing.*;

/**
 * Created by hadgehog on 16.02.14.
 */
public class ExitMenu {
    public static JMenu getMenu() {
        JMenu exitMenu = new JMenu("Вихід");
        JMenuItem saveExitMenuItem = new JMenuItem("Зберегти і вийти");
        saveExitMenuItem.addActionListener(new SaveExitProgramListener());

        JMenuItem hardExitMenuItem1 = new JMenuItem("Вийти");
        hardExitMenuItem1.addActionListener(new HardExitProgramListener());

        exitMenu.add(saveExitMenuItem);
        exitMenu.add(hardExitMenuItem1);
        return exitMenu;
    }
}
