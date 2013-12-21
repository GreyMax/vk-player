package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.listeners.playlists.PlaylistClickListener;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableModel;

import java.util.List;


public class MyMusicPlaylist extends AbstractPlaylist{

    protected void init() {
        setModel(new PlaylistTableModel(audioService.getMySongsForPlaylist(playerForm.getLoggedUser()), cols));
        setShowGrid(false);
        setName("MyMusic");
        setFocusable(false);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(20);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
        playerService.setListSongs(audioService.getMySongsList());
        addMouseListener(new PlaylistClickListener());
    }

    @Override
    public List<Song> getSongsList() {
        return audioService.getMySongsList();
    }

    public void refresh() {
        int index = getSelectedSongIndex();
        setModel(new PlaylistTableModel(audioService.getMySongsForPlaylist(playerForm.getLoggedUser()), cols));
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(20);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
        changeSelection(index, 1, false, false);
    }
}
