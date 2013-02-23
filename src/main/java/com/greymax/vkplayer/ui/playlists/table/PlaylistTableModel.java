package com.greymax.vkplayer.ui.playlists.table;

import com.greymax.vkplayer.Utils;

import javax.swing.table.AbstractTableModel;


public class PlaylistTableModel extends AbstractTableModel {
    String[] columnTitles;
    Object[][] dataEntries;

    int rowCount;

    public PlaylistTableModel(Object[][] dataEntries, String[] columnTitles) {
        this.columnTitles = columnTitles;
        this.dataEntries = dataEntries;
    }

    public int getRowCount() {
        return dataEntries.length;
    }

    public int getColumnCount() {
        return columnTitles.length;
    }

    public Object getValueAt(int row, int column) {
        return dataEntries[row][column];
    }

    public String getColumnName(int column) {
        return Utils.getMessageByKey(columnTitles[column]);
    }

    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void setValueAt(Object value, int row, int column) {
        dataEntries[row][column] = value;
    }

}