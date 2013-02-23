package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.objects.Song;

import java.util.ArrayList;
import java.util.List;

public enum Playlist {

    MyMusic(Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX,
            Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_NAME,
            new MyMusicPlaylist()),
    SuggestedMusic(Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_INDEX,
            Constants.PLAYER.PLAYLIST.SUGGESTED_MUSIC_TAB_NAME,
            new SuggestedMusicPlayList()),
    FriendsMusic(Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_INDEX,
            Constants.PLAYER.PLAYLIST.FRIENDS_MUSIC_TAB_NAME,
            new FriendsMusicPlaylist()),
    SearchMusic(Constants.PLAYER.PLAYLIST.SEARCH_TAB_INDEX,
            Constants.PLAYER.PLAYLIST.SEARCH_TAB_NAME,
            new SearchMusicPlaylist());

    private int id;
    private String titleKey;
    private AbstractPlaylist playlist;

    Playlist(int tabIndex, String tabName, AbstractPlaylist playlist) {
        this.id = tabIndex;
        this.titleKey = tabName;
        this.playlist = playlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public AbstractPlaylist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(AbstractPlaylist playlist) {
        this.playlist = playlist;
    }

    public static AbstractPlaylist getPlaylistByTabIndex(int tabIndex) {
        for (Playlist pl : Playlist.values())
            if(pl.getId() == tabIndex)
                return pl.getPlaylist();

        return null;
    }

    public static void highlightPlaingSong(int songIndex, int tabIndex) {
        AbstractPlaylist apl = getPlaylistByTabIndex(tabIndex);
        if (null != apl)
            apl.highlightPlaingSong(songIndex);
    }

    public static int getLastSongIndex(int tabIndex) {
        int lastSongIndex = 0;
        AbstractPlaylist apl = getPlaylistByTabIndex(tabIndex);
        if (null != apl)
            lastSongIndex = apl.getLastSongIndex();

        return lastSongIndex;
    }

    public static int getSelectedSongIndex(int tabIndex) {
        int selectedSongIndex = 0;
        AbstractPlaylist apl = getPlaylistByTabIndex(tabIndex);
        if (null != apl)
            selectedSongIndex = apl.getSelectedSongIndex();

        return selectedSongIndex;
    }

    public static List<Song> getSongsList(int tabIndex) {
        List<Song> songs = new ArrayList<>();
        AbstractPlaylist apl = getPlaylistByTabIndex(tabIndex);
        if (null != apl)
            songs = apl.getSongsList();

        return songs;
    }

}
