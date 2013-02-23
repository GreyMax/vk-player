package com.greymax.vkplayer.listeners.playlists;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.services.AudioService;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.playlists.menu.PopupMenu;
import com.greymax.vkplayer.ui.playlists.Playlist;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PlaylistClickListener extends MouseAdapter {

    //TODO: move to constants
    private static AudioService audioService = AudioService.getInstance();
    private static FXPlayerService playerService = FXPlayerService.getInstance();
    private static VkPlayerForm playerForm = VkPlayerForm.getInstance();
    private static final String SEARCH = "SearchMusic";
    private static final String FRIENDS = "FriendsMusic";
    private static final String MY = "MyMusic";
    private static final String SUGGESTED = "SuggestedMusic";
    private static final String RADIO = "Radio";

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger() && !((JTable) e.getSource()).getName().equals(RADIO)) {
            JTable table = ((JTable) e.getSource());
            int r = table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < table.getRowCount())
                table.setRowSelectionInterval(r, r);
            else
                table.clearSelection();

            int rowIndex = table.getSelectedRow();
            if (rowIndex < 0)
                return;

            doPop(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
        JTable target = (JTable)e.getSource();
        if (e.getClickCount() == 2) {
            if(target.getName().equals(MY))
                playerService.setListSongs(audioService.getMySongsList());

            if(target.getName().equals(SUGGESTED))
                playerService.setListSongs(audioService.getSuggestedSongsList());

            if(target.getName().equals(FRIENDS))
                playerService.setListSongs(audioService.getFriendsSongsList());

            if(target.getName().equals(SEARCH))
                playerService.setListSongs(audioService.getSearchSongsList());

            playerService.setIsPaused(false);
            playerService.play(Playlist.getSelectedSongIndex(playerForm.getActiveTabIndex()), playerForm.getActiveTabIndex());
        }
    }

    private void doPop(MouseEvent e){
        int index = 0;
        String playlistName = ((JTable)e.getSource()).getName();
        if (playlistName.equals(MY))
            index = Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX;

        if (playlistName.equals(SUGGESTED))
            index = Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_INDEX;

        if (playlistName.equals(FRIENDS))
            index = Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_INDEX;

        if (playlistName.equals(SEARCH))
            index = Constants.PLAYER.PLAYLIST.SEARCH_TAB_INDEX;

        PopupMenu menu = new PopupMenu(index);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
