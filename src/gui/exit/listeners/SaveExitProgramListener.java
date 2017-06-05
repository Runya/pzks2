package gui.exit.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Vadim Petruk  on 2/8/14.
 */
public class SaveExitProgramListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Вікно збереження даних. Вихід");
        System.exit(0);
    }
}
