package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.listeners.playlists.PlaylistClickListener;
import com.greymax.vkplayer.services.FXPlayerService;

import javax.swing.*;

@Deprecated
public class RadioPlaylist extends JTable{

    private static FXPlayerService playerService = FXPlayerService.getInstance();

    public RadioPlaylist() {
        String[] cols = {Constants.PLAYER.PLAYLIST.PLAYLIST_TABLE_HEADER_NUMBER,
                Constants.PLAYER.PLAYLIST.PLAYLIST_TABLE_HEADER_RADIO};
//        setModel(new PlaylistTableModel(audioService.getArrayRadio(), cols));
        setFocusable(false);
        setShowGrid(false);
        setName("Radio");
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(220);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
//        playerService.setListSongs(audioService.getRadioList());
        addMouseListener(new PlaylistClickListener());
    }
}
