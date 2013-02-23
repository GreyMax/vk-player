package com.greymax.vkplayer.listeners.control.panel;

import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.playlists.Playlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlActionListener implements ActionListener{

    private static FXPlayerService playerService = FXPlayerService.getInstance();
    private static VkPlayerForm playerForm = VkPlayerForm.getInstance();
    private static final String PLAY = "Play";
    private static final String PAUSE = "Pause";
    private static final String STOP = "Stop";
    private static final String PREV = "Prev";
    private static final String NEXT = "Next";
    private static final String MUTE = "Mute";
    private static final String RANDOM = "Random";
    private static final String REPLAY = "Replay";
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.equals(PLAY)) {
            int activeTab = playerForm.getActiveTabIndex();
            playerService.setListSongs(Playlist.getSongsList(activeTab));
            playerService.play(Playlist.getSelectedSongIndex(activeTab) == -1
                    ? 0 : Playlist.getSelectedSongIndex(activeTab), activeTab);
        }

        if(cmd.equals(PAUSE))
            playerService.pause();

        if(cmd.equals(STOP))
            playerService.stop();

        if(cmd.equals(PREV))
            playerService.prev();

        if(cmd.equals(NEXT))
            playerService.next();

        if(cmd.equals(MUTE))
            playerService.toggleMute();

        if(cmd.equals(RANDOM))
            playerService.toggleRandom();

        if(cmd.equals(REPLAY))
            playerService.toggleReplay();
    }
}
