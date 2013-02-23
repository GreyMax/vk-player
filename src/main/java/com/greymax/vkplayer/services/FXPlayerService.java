package com.greymax.vkplayer.services;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.ui.SongInformationDialog;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.playlists.Playlist;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FXPlayerService implements AudioSpectrumListener, Runnable {

    private static Logger logger = Logger.getLogger(FXPlayerService.class);

    private static FXPlayerService instance;
    private List<Song> songsList;
    private Song currentSong;
    private MediaPlayer player;
    private double beforeMute;
    private boolean isMuted = false;
    private boolean isPaused = false;
    private boolean isResume = true;
    private boolean isRandom = false;
    private boolean isReplay = false;
    private int currentSongIndex;
    private int currentPlayingTab = 0;
    private boolean isBuffering = true;
    private float[] equalizer = {0.0f,0.0f,0.0f,0.0f,0.0f,
                                 0.0f,0.0f,0.0f,0.0f,0.0f};
    private final double UPDATE_INTERVAL = 0.01d;

    private static VkPlayerForm playerForm = VkPlayerForm.getInstance();
    private static AudioService audioService = AudioService.getInstance();
    private static StatusService statusService = StatusService.getInstance();

    public static FXPlayerService getInstance() {
        if (instance == null)
            instance = new FXPlayerService();

        return instance;
    }

    private int getRandomSongIndex() {
        return Utils.getRandomIndex(songsList.size());
    }

    public void setListSongs(List<Song> songs) {
        this.songsList = songs;
    }

    public void play(int index, int playingTab) {
        try {
            currentSongIndex = index;
            currentPlayingTab = playingTab;
            currentSong = songsList.get(currentSongIndex);

            if(isPaused && isResume) {
                resume();
            } else {
                if( null != player) stop();
                Playlist.highlightPlaingSong(index, currentPlayingTab);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new JFXPanel();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                                    URL songUrl = new URL(currentSong.getUrl());
                                    Media media = new Media(songUrl.toString());
                                    player = new MediaPlayer(media);
                                    player.setOnReady(instance);
                                    player.setOnPlaying(instance);
                                    player.setOnEndOfMedia(new PlayerEOMListener());
                                    player.setAudioSpectrumInterval(UPDATE_INTERVAL);
                                    player.setAudioSpectrumListener(instance);
                                    player.play();
                                    isBuffering = true;
                                    setVolume(playerForm.getControlPanel().getVolumePosition());
                                    setEqualizer(Boolean.TRUE, 0);
                                    if(isMuted) setVolume((double) 0);
                                    if (currentPlayingTab != Constants.PLAYER.PLAYLIST.RADIO_TAB_INDEX)
                                        statusService.setUserStatus(playerForm.getLoggedUser(), currentSong.getId().toString(), true);
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void setEqGainValue(int gain, float gainValue) {
        equalizer[gain] = gainValue;
        if(null != player)
            setEqualizer(Boolean.FALSE, gain);
    }

    protected void setEqualizer(boolean setAllGain, int gain) {
        List<EqualizerBand> equalizerBands = player.getAudioEqualizer().getBands();
        if(setAllGain)
            for (int i = 0; i < equalizerBands.size(); i++)
                equalizerBands.get(i).setGain(equalizer[i]);
        else if (gain < 10)
            equalizerBands.get(gain).setGain(equalizer[gain]);
    }

    public float[] getEqualizer() {
        return equalizer;
    }

    public void stop() {
        if (null == player) return;
        try{
            player.stop();
            playerForm.getControlPanel().setCurrentSeekPos(0);
            playerForm.getControlPanel().setSeekMaxTime(0);
        }catch (Exception e){
            logger.error("Stop player error:", e);
        }
    }

    public void pause() {
        if (null == player) return;
        try{
            player.pause();
            this.isPaused = true;
            this.isResume = true;
        }catch (Exception e){
            logger.error("Pause player error:", e);
        }
    }

    public void resume() {
        if (null == player) return;
        try{
            player.play();
            this.isPaused = false;
        }catch (Exception e){
            logger.error("Resume player error:", e);
        }
    }

    public void prev() {
        int index = Playlist.getSelectedSongIndex(currentPlayingTab) == 0 || Playlist.getSelectedSongIndex(currentPlayingTab) == -1
                ? Playlist.getLastSongIndex(currentPlayingTab)
                : Playlist.getSelectedSongIndex(currentPlayingTab) - 1;
        this.isResume = false;
        play(isRandom ? getRandomSongIndex() : index, currentPlayingTab);
    }

    public void next() {
        int index = Playlist.getSelectedSongIndex(currentPlayingTab) == Playlist.getLastSongIndex(currentPlayingTab)
                ? 0 : Playlist.getSelectedSongIndex(currentPlayingTab) + 1;
        this.isResume = false;
        play(isRandom ? getRandomSongIndex() : index, currentPlayingTab);
    }

    public void seek(int pos, boolean isForward) {
        if (null == player)
            return;

        Duration newDuration = new Duration(new Double(String.valueOf(pos)));
        player.seek(newDuration);
    }

    public void setVolume(double volume) {
        if (null == player)
            return;

        player.setVolume(volume);
    }

    public void setIsMuted(boolean flag) {
        this.isMuted = flag;
        if(flag) setVolume(0);
    }

    public boolean getIsMuted() {
        return isMuted;
    }

    public void toggleRandom() {
        isRandom = !isRandom;
        playerForm.getControlPanel().setRandomIcon(isRandom);
    }

    public void toggleReplay() {
        isReplay = !isReplay;
        playerForm.getControlPanel().setReplayIcon(isReplay);
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean flag) {
        isPaused = flag;
    }

    public boolean getIsPlaying(){
        return player != null && player.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public void toggleMute() {
        try{
            if(getIsMuted()) {
                setVolume(beforeMute);
                setIsMuted(false);
                playerForm.getControlPanel().setVolumePosition(new Double(beforeMute * 100).intValue());
                playerForm.getControlPanel().setMuteIcon(false);
            } else {
                this.beforeMute = playerForm.getControlPanel().getVolumePosition();
                playerForm.getControlPanel().setVolumePosition(0);
                setIsMuted(true);
                playerForm.getControlPanel().setMuteIcon(isMuted);
            }
        }catch (Exception e){
            logger.error("Toggle mute error:", e);
        }
    }

    //TODO: Extract get songs into new method
    public void downloadSong() {
        int tab = playerForm.getActiveTabIndex();
        int index = Playlist.getSelectedSongIndex(tab);
        try {
            List<Song> songs = getSongsByTab(tab);
            playerForm.saveSong(songs.get(index));
        } catch (Exception e) {
            logger.error("Download song error:", e);
        }
    }

    public void addSongToMyMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int tab = playerForm.getActiveTabIndex();
                    int index = Playlist.getSelectedSongIndex(tab);
                    List<Song> songs = getSongsByTab(tab);
                    audioService.addSongToMyMusic(playerForm.getLoggedUser(), songs.get(index));
                    playerForm.getMyMusicPlaylist().refresh();
                } catch (Exception e) {
                    logger.error("Add song to my music error:", e);
                }
            }
        }).start();
    }

    public void deleteSongFromMyMusic() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    int tab = playerForm.getActiveTabIndex();
                    int index = Playlist.getSelectedSongIndex(tab);
                    List<Song> songs = getSongsByTab(tab);
                    audioService.deleteSongFromMyMusic(playerForm.getLoggedUser(), songs.get(index));
                    playerForm.getMyMusicPlaylist().refresh();
                } catch (Exception e) {
                    logger.error("Delete song from my music error:", e);
                }
            }
        }).start();
    }

    public void postSongToWall(User friend) {
        try {
            int tabIndex = playerForm.getActiveTabIndex();
            int songIndex = Playlist.getSelectedSongIndex(tabIndex);
            List<Song> songs = getSongsByTab(tabIndex);
            WallService.getInstance().postAudio(playerForm.getLoggedUser().getToken(), friend, songs.get(songIndex));
        } catch (Exception e) {
            logger.error("Can not post audio to wall:", e);
        }
    }

    private List<Song> getSongsByTab(int tabIndex) {
        List<Song> songs = new ArrayList<>();
        switch (tabIndex) {
            case Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX: {
                songs = AudioService.getInstance().getMySongsList();
                break;
            }
            case Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_INDEX: {
                songs = AudioService.getInstance().getFriendsSongsList();
                break;
            }
            case Constants.PLAYER.PLAYLIST.SEARCH_TAB_INDEX: {
                songs = AudioService.getInstance().getSearchSongsList();
                break;
            }
            case Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_INDEX: {
                songs = AudioService.getInstance().getSuggestedSongsList();
                break;
            }
        }
        return songs;
    }


    // ==========  Player Listeners ==============

    @Override
    public void spectrumDataUpdate(double v, double v1, float[] floats, float[] floats1) {
        //TODO: think how to fix slider issue (maybe decrease update interval)
        int currentTime = new Double(v * 10e2).intValue();
        playerForm.getControlPanel().setCurrentSeekPos(currentTime);
        if (isBuffering)
            if (player != null && player.getTotalDuration() != null && player.getBufferProgressTime() != null) {
                double bufferingProgress = player.getBufferProgressTime().toMillis();
                if (bufferingProgress < player.getTotalDuration().toMillis())
                    playerForm.getControlPanel().updateBufferingProgress(new Double(bufferingProgress).intValue());
                else {
                    isBuffering = Boolean.FALSE;
                    playerForm.getControlPanel().updateBufferingProgress(new Double(player.getTotalDuration().toMillis()).intValue());
                }
            }
    }

    @Override
    public void run() {
        playerForm.getControlPanel().setSeekMaxTime(new Double(player.getTotalDuration().toMillis()).intValue());
        if(playerForm.getLoggedUser().getSettings().getNeedShowInformationDialog())
            SongInformationDialog.getInstance().showSong(currentSong);
    }

    class PlayerEOMListener implements Runnable {

        @Override
        public void run() {
            if (!isReplay) next();
            else play(currentSongIndex, currentPlayingTab);
        }
    }

}
