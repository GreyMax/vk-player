package com.greymax.vkplayer.listeners.playlists;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.ui.VkPlayerForm;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;


public class SearchFieldListener implements KeyListener, FocusListener{

    private List<String> MESSAGES = Arrays.asList("Type here for search...",
            "Введіть текст для пошуку...",
            "Введите текст для поиска...");

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchText = ((JTextField) e.getSource()).getText();
            VkPlayerForm.getInstance().getSearchPlaylist().search(searchText);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        String text = Utils.getMessageByKey(Constants.PLAYER.PLAYLIST.SEARCH_FIELD_MESSAGE);
        if(MESSAGES.contains(field.getText()))
            field.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
//        String text = Utils.getMessageByKey(Constants.PLAYER.PLAYLIST.SEARCH_FIELD_MESSAGE);
        if("".equals(field.getText()))
            field.setText(Constants.PLAYER.PLAYLIST.SEARCH_FIELD_MESSAGE);
    }
}
