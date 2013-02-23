package com.greymax.vkplayer.ui.downloadForm;

import javax.swing.*;
import java.awt.*;


public class CustomCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent (JList list, Object value, int index,
                                                   boolean isSelected,boolean cellHasFocus) {
        Component component = (Component) value;
        component.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        component.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
//        component.setBackground(isSelected ? Color.DARK_GRAY : Color.GRAY);
//        component.setForeground(isSelected ? Color.WHITE : Color.black);
        component.setPreferredSize(new Dimension(100,60));
        return component;
    }
}
