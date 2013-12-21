package com.greymax.vkplayer.listeners.playlists.menu;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.playlists.dialogs.EditSongDialog;
import com.greymax.vkplayer.ui.playlists.dialogs.FriendsDialog;
import com.greymax.vkplayer.ui.playlists.dialogs.SongLyricsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PopupMenuListener implements ActionListener{

    private FXPlayerService playerService = FXPlayerService.getInstance();
    private JFrame friendsDialogOwner;
    private JFrame editDialogOwner;
    private JFrame lyricsDialogOwner;
    public static final String DOWNLOAD = "Download";
    public static final String ADD = "Add";
    public static final String DELETE = "Delete";
    public static final String WALL_POST = "WallPost";
    public static final String EDIT = "Edit";
    public static final String LYRICS = "Lyrics";

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(DOWNLOAD))
            playerService.downloadSong();

        if (e.getActionCommand().equals(ADD))
            playerService.addSongToMyMusic();

        if (e.getActionCommand().equals(DELETE))
            playerService.deleteSongFromMyMusic();

        if (e.getActionCommand().equals(WALL_POST))
            new FriendsDialog(friendsDialogOwner, Constants.PLAYER.PLAYLIST.POPUP_MENU_POST_TO_WALL);

        if (e.getActionCommand().equals(EDIT))
            new EditSongDialog(editDialogOwner, Constants.PLAYER.EDIT_DIALOG.TITLE);

        if (e.getActionCommand().equals(LYRICS))
            new SongLyricsDialog(lyricsDialogOwner, Constants.PLAYER.LYRICS_DIALOG.TITLE);
    }
}
