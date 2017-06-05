package gui.task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.utils.Utils_GUI;

/**
 * Created by hadgehog on 16.02.14.
 */
public class EditTaskTools extends JToolBar {
    private EditTaskGraphPanel editTaskGraphPanel;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public EditTaskTools(EditTaskGraphPanel editTaskGraphPanel) {
        this.editTaskGraphPanel = editTaskGraphPanel;
        setOrientation(SwingConstants.VERTICAL);
        setFloatable(false);

        Utils_GUI utilsGui  = new Utils_GUI();

        JButton mouseArrowButton = utilsGui.makeBarJButton("/gui/icons/task/cursor_arrow_icon.png", "");
        mouseArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editTaskGraphPanel.setActiveTaskToolType(EditTaskGraphPanel.TaskToolType.MOUSE);
                editTaskGraphPanel.getTaskGraphPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        add(mouseArrowButton);
        JButton newVertexButton = utilsGui.makeBarJButton("/gui/icons/task/new_vertex_icon.png", "Нова вершина графа");
        newVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editTaskGraphPanel.setActiveTaskToolType(EditTaskGraphPanel.TaskToolType.NEW_VERTEX);
                Image image = utilsGui.getScalableIcon("/gui/icons/task/new_vertex_icon.png");
                Cursor newVertexPointer = toolkit.createCustomCursor(image, new Point(editTaskGraphPanel.getX(),
                        editTaskGraphPanel.getY()), "new vertex");
                editTaskGraphPanel.getTaskGraphPanel().setCursor(newVertexPointer);
            }
        });
        add(newVertexButton);

        JButton newLinkButton = utilsGui.makeBarJButton("/gui/icons/task/link_icon.png", "Нове ребро графа");
        newLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editTaskGraphPanel.setActiveTaskToolType(EditTaskGraphPanel.TaskToolType.CONNECTION);
                Image image = utilsGui.getScalableIcon("/gui/icons/task/link_icon.png");
                Cursor connectionPointer = toolkit.createCustomCursor(image, new Point(editTaskGraphPanel.getX(),
                        editTaskGraphPanel.getY()), "connection");
                editTaskGraphPanel.getTaskGraphPanel().setCursor(connectionPointer);
            }
        });
        add(newLinkButton);

        JButton deleteButton =  utilsGui.makeBarJButton("/gui/icons/task/delete_icon.png", "Видалити(вершину/ребро)");;
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editTaskGraphPanel.setActiveTaskToolType(EditTaskGraphPanel.TaskToolType.REMOVE);
                Image image = utilsGui.getScalableIcon("/gui/icons/task/delete_icon_pointer.png");
                Cursor removePointer = toolkit.createCustomCursor(image, new Point(editTaskGraphPanel.getX(),
                        editTaskGraphPanel.getY()), "remove");
                editTaskGraphPanel.getTaskGraphPanel().setCursor(removePointer);
            }
        });
        add(deleteButton);
    }
}
