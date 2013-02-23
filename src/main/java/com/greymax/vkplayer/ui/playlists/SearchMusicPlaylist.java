package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.listeners.playlists.PlaylistClickListener;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableModel;
import org.apache.commons.lang.StringUtils;

import java.util.List;


public class SearchMusicPlaylist extends AbstractPlaylist{

    protected void init() {
        setModel(new PlaylistTableModel(new String[0][], cols));
        setShowGrid(false);
        setName("SearchMusic");
        setFocusable(false);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(20);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
        addMouseListener(new PlaylistClickListener());
    }

    @Override
    public List<Song> getSongsList() {
        return audioService.getSearchSongsList();
    }

    public void search(String search) {
        if(null != search && !StringUtils.EMPTY.equals(search.trim())) {
            setModel(new PlaylistTableModel(
                    audioService.getSongsBySearchForPlaylist(playerForm.getLoggedUser(), search), cols));
            getColumnModel().getColumn(0).setPreferredWidth(15);
            getColumnModel().getColumn(1).setPreferredWidth(200);
            getColumnModel().getColumn(2).setPreferredWidth(20);
            getColumnModel().getColumn(0).setResizable(false);
            getColumnModel().getColumn(1).setResizable(false);
            getColumnModel().getColumn(2).setResizable(false);
            playerService.setListSongs(audioService.getSearchSongsList());
        }
    }
}
