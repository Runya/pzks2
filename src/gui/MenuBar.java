package gui;

import gui.exit.ExitMenu;
import gui.help.HelpMenu;
import gui.modeling.ModellingMenu;
import gui.system.SystemMenu;
import gui.task.TaskMenu;
import utils.WorkManager;
   
import javax.swing.*;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class MenuBar extends JMenuBar {
    public MenuBar(WorkManager workManager) {
        add(TaskMenu.getMenu(workManager));
        add(SystemMenu.getMenu(workManager));
        add(ModellingMenu.getMenu(workManager));
        add(HelpMenu.getMenu());
//        add(ExitMenu.getMenu());
    }
}
