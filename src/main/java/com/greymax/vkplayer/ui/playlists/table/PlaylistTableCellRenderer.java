package com.greymax.vkplayer.ui.playlists.table;

import org.jdesktop.swingx.color.ColorUtil;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class PlaylistTableCellRenderer extends JLabel implements TableCellRenderer {

    private int highlightedRowNum;

    public PlaylistTableCellRenderer(int rownum) {
        this.highlightedRowNum = rownum;
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value != null) setText(value.toString());
        if(isSelected)
            setBackground(table.getSelectionBackground());
        else {
            setBackground(table.getBackground());
            if(row == highlightedRowNum)
                setBackground(ColorUtil.setAlpha(table.getSelectionBackground(), 150));

        }

        return this;
    }

}
