package com.greymax.vkplayer.ui.playlists.menu;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.listeners.playlists.menu.PopupMenuListener;
import com.greymax.vkplayer.ui.multilanguage.objects.JMenuItemML;

import javax.swing.*;


public class PopupMenu extends JPopupMenu {

    private PopupMenuListener listener = new PopupMenuListener();

    public PopupMenu(int tab) {
        // download menu
        JMenuItem download = new JMenuItemML(Constants.PLAYER.PLAYLIST.POPUP_MENU_DOWNLOAD);
        download.setActionCommand(PopupMenuListener.DOWNLOAD);
        download.addActionListener(listener);

        // add to my music menu
        JMenuItem addToMyMusic = new JMenuItemML(Constants.PLAYER.PLAYLIST.POPUP_MENU_ADD_TO_MY);
        addToMyMusic.setActionCommand(PopupMenuListener.ADD);
        addToMyMusic.addActionListener(listener);

        // delete from my music menu
        JMenuItem deleteFromMyMusic = new JMenuItemML(Constants.PLAYER.PLAYLIST.POPUP_MENU_DELETE_FROM_MY);
        deleteFromMyMusic.setActionCommand(PopupMenuListener.DELETE);
        deleteFromMyMusic.addActionListener(listener);

        // post audio to wall
        JMenuItem postToWall = new JMenuItemML(Constants.PLAYER.PLAYLIST.POPUP_MENU_POST_TO_WALL);
        postToWall.setActionCommand(PopupMenuListener.WALL_POST);
        postToWall.addActionListener(listener);

        // edit song
        JMenuItem editSong = new JMenuItemML(Constants.PLAYER.PLAYLIST.POPUP_MENU_EDIT);
        editSong.setActionCommand(PopupMenuListener.EDIT);
        editSong.addActionListener(listener);

        if ( tab == Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX
                || tab == Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_INDEX
                || tab == Constants.PLAYER.PLAYLIST.SEARCH_TAB_INDEX
                || tab == Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_INDEX ) {
            add(download);
            add(postToWall);
        }

        if ( tab == Constants.PLAYER.PLAYLIST.SEARCH_TAB_INDEX
                || tab == Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_INDEX
                || tab == Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_INDEX )
            add(addToMyMusic);

        if (tab == Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX) {
            add(editSong);
            add(deleteFromMyMusic);
        }
    }
}
