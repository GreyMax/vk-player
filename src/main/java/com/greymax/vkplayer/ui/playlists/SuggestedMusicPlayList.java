package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.listeners.playlists.PlaylistClickListener;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableModel;

import java.util.List;

public class SuggestedMusicPlayList extends AbstractPlaylist {

    protected void init() {
        setModel(new PlaylistTableModel(audioService.getSuggestedSongsForPlaylist(playerForm.getLoggedUser()), cols));
        setShowGrid(false);
        setName("SuggestedMusic");
        setFocusable(false);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(20);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
//        playerService.setListSongs(audioService.getMyListSongs());
        addMouseListener(new PlaylistClickListener());
    }

    @Override
    public List<Song> getSongsList() {
        return audioService.getSuggestedSongsList();
    }

    @Override
    public void refresh() {
        // TODO: implement me please!
        throw new UnsupportedOperationException();
    }
}
