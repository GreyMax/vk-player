package com.greymax.vkplayer.ui.playlists;

import com.greymax.vkplayer.listeners.playlists.PlaylistClickListener;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableModel;

import java.util.List;


public class FriendsMusicPlaylist extends AbstractPlaylist {

    protected void init() {
        setModel(new PlaylistTableModel(new String[0][], cols));
        setShowGrid(false);
        setName("FriendsMusic");
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
        return audioService.getFriendsSongsList();
    }

    public void showMusicForUser(User friend) {
        if(null != friend && null != friend.getUid()) {
            setModel(new PlaylistTableModel(
                    audioService.getFriendsSongsForPlaylist(
                            playerForm.getLoggedUser(), friend.getUid().toString()),cols
                    )
            );
            playerService.setListSongs(audioService.getFriendsSongsList());
        } else {
            setModel(new PlaylistTableModel(new String[0][], cols));
        }
        getColumnModel().getColumn(0).setPreferredWidth(15);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(20);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
    }
}
