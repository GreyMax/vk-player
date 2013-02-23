package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.services.AudioService;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableCellRenderer;

import javax.swing.*;
import java.util.List;

public abstract class AbstractPlaylist extends JTable {

    protected static AudioService audioService = AudioService.getInstance();
    protected static FXPlayerService playerService = FXPlayerService.getInstance();
    protected static VkPlayerForm playerForm = VkPlayerForm.getInstance();

    String[] cols = {Constants.PLAYER.PLAYLIST.PLAYLIST_TABLE_HEADER_NUMBER,
            Constants.PLAYER.PLAYLIST.PLAYLIST_TABLE_HEADER_NAME,
            Constants.PLAYER.PLAYLIST.PLAYLIST_TABLE_HEADER_TIME };

    public AbstractPlaylist() {
        init();
    }

    protected abstract void init();
    public abstract List<Song> getSongsList();

    public void highlightPlaingSong(int index) {
        changeSelection(index, 1, false, false);
        setDefaultRenderer(Object.class, new PlaylistTableCellRenderer(index));
        repaint();
    }

    public int getLastSongIndex() {
        return this.getRowCount()-1;
    }

    public int getSelectedSongIndex() {
        return this.getSelectedRow();
    }

}
