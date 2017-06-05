package gui.system;

import gui.utils.Utils_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hadgehog on 16.02.14.
 */
public class EditSystemTools extends JToolBar {
    private EditSystemGraphPanel editSystemGraphPanel;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public EditSystemTools(EditSystemGraphPanel editSystemGraphPanel) {
        this.editSystemGraphPanel = editSystemGraphPanel;
        setOrientation(SwingConstants.VERTICAL);
        setFloatable(false);

        Utils_GUI utilsGui = new Utils_GUI();

        JButton mouseArrowButton = utilsGui.makeBarJButton("/gui/icons/system/cursor_arrow_icon.png", "");;
        mouseArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editSystemGraphPanel.setActiveSystemToolType(EditSystemGraphPanel.SystemToolType.MOUSE);
                editSystemGraphPanel.getSystemGraphPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        add(mouseArrowButton);

        JButton newVertexButton = utilsGui.makeBarJButton("/gui/icons/system/new_vertex_icon.png", "Нова вершина графа");;
        newVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editSystemGraphPanel.setActiveSystemToolType(EditSystemGraphPanel.SystemToolType.NEW_VERTEX);
                Image image = utilsGui.getScalableIcon("/gui/icons/system/new_vertex_icon.png");
                Cursor newVertexPointer = toolkit.createCustomCursor(image, new Point(0, 0), "new vertex");
                editSystemGraphPanel.getSystemGraphPanel().setCursor(newVertexPointer);
            }
        });
        add(newVertexButton);

        JButton newLinkButton = utilsGui.makeBarJButton("/gui/icons/system/link_icon.png", "");;
        newLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editSystemGraphPanel.setActiveSystemToolType(EditSystemGraphPanel.SystemToolType.CONNECTION);
                Image image = utilsGui.getScalableIcon("/gui/icons/system/link_icon_pointer.png");
                Cursor connectionPointer = toolkit.createCustomCursor(image, new Point(0, 0), "connection");
                editSystemGraphPanel.getSystemGraphPanel().setCursor(connectionPointer);
            }
        });
        newLinkButton.setToolTipText("Нове ребро графа");
        add(newLinkButton);

        JButton deleteButton = utilsGui.makeBarJButton("/gui/icons/system/delete_icon.png", "Видалити(вершину/ребро)");;
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editSystemGraphPanel.setActiveSystemToolType(EditSystemGraphPanel.SystemToolType.REMOVE);
                Image image = utilsGui.getScalableIcon("/gui/icons/system/delete_icon_pointer.png");
//                Image image = toolkit.getImage("src\\gui\\icons\\system\\delete_icon.png");
                Cursor removePointer = toolkit.createCustomCursor(image, new Point(0, 0), "remove");
                editSystemGraphPanel.getSystemGraphPanel().setCursor(removePointer);
            }
        });
        add(deleteButton);
    }
}
