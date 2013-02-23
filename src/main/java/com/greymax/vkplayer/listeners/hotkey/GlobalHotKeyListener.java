package com.greymax.vkplayer.listeners.hotkey;

import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.playlists.Playlist;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class GlobalHotKeyListener {

    private static final Logger logger = Logger.getLogger(GlobalHotKeyListener.class);

    private Provider provider;
    private JFrame controllingFrame;
    private VkPlayerForm playerForm = VkPlayerForm.getInstance();
    private FXPlayerService playerService = FXPlayerService.getInstance();

    public GlobalHotKeyListener(){
        try{
            provider = Provider.getCurrentProvider(true);
            HotKeyListener listener = new HotKeyListener() {
                public void onHotKey(HotKey hotKey) {
                    // Play, Pause, Resume
                    System.out.println(hotKey.toString());
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK)))
                        if(playerService.getIsPaused()) {
                            playerService.resume();
                        } else {
                            if(playerService.getIsPlaying()) {
                                playerService.pause();
                            } else {
                                playerService.play(Playlist.getSelectedSongIndex(playerForm.getActiveTabIndex()) == -1
                                        ? 0 : Playlist.getSelectedSongIndex(playerForm.getActiveTabIndex()),
                                        playerForm.getActiveTabIndex());
                            }
                        }
                    // Volume Up
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.ALT_MASK))) {
                        int currentVolume = (new Double(playerForm.getControlPanel().getVolumePosition() * 100).intValue());
                        if(currentVolume + 10 < 100) {
                            playerForm.getControlPanel().setVolumePosition(currentVolume + 10);
                        } else {
                            playerForm.getControlPanel().setVolumePosition(100);
                        }
                    }
                    // Volume Down
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.ALT_MASK))) {
                        int currentVolume = (new Double(playerForm.getControlPanel().getVolumePosition() * 100)).intValue();
                        if(currentVolume - 10 > 0) {
                            playerForm.getControlPanel().setVolumePosition(currentVolume - 10);
                        } else {
                            playerForm.getControlPanel().setVolumePosition(0);
                        }
                    }
                    // Next Track
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.ALT_MASK))) {
                        playerService.next();
                    }
                    // Previous Track
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.ALT_MASK))) {
                        playerService.prev();
                    }
                    // Mute
                    if (hotKey.keyStroke.equals(KeyStroke.getKeyStroke(KeyEvent.VK_END, InputEvent.ALT_MASK))) {
                        playerService.toggleMute();
                    }

                }
            };
            provider.reset();
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.ALT_MASK), listener);
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.ALT_MASK), listener);
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.ALT_MASK), listener);
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP,InputEvent.ALT_MASK), listener);
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN,InputEvent.ALT_MASK), listener);
            provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_END,InputEvent.ALT_MASK), listener);
        }catch (Exception ex) {
            logger.error(ex);
        }
    }

    public Provider getProvider() {
        return provider;
    }

}
