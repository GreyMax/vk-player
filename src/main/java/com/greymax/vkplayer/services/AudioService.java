package com.greymax.vkplayer.services;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.ApiMethods;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.ui.VkPlayerForm;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AudioService {
    
    private static final Logger logger = Logger.getLogger(AudioService.class);

    private static AudioService instance = null;
    private List<Song> mySongsList;
    private List<Song> friendsSongsList;
    private List<Song> searchSongsList;
    private List<Song> suggestedSongsList;

    public static AudioService getInstance() {
        if (instance == null)
            instance = new AudioService();
        
        return instance;
    }

    public String[][] getMySongsForPlaylist(User loggedUser) {
        String[][] list = new String[0][];
        mySongsList = new ArrayList<>();
        try{
            JSONArray mySongs = make(loggedUser, ApiMethods.Audio.GET, new P());

            list = new String[mySongs.length()][3];
            for(int i = 0; i < mySongs.length(); i++){
                Song song = new Song(mySongs.getJSONObject(i));
                mySongsList.add(song);
                list[i][0] = String.valueOf(i+1);
                list[i][1] = song.getDisplayName();
                list[i][2] = song.getDurationForDisplay();
            }
        }catch (Exception e){
            logger.error("Get my songs:", e);
        }
        return list;
    }

    public String[][] getSuggestedSongsForPlaylist(User loggedUser) {
        String[][] list = new String[0][];
        suggestedSongsList = new ArrayList<>();
        try{
            P params = new P("count=200");
            JSONArray suggestedSongs = make(loggedUser, ApiMethods.Audio.GET_RECOMMENDATIONS, params);

            list = new String[suggestedSongs.length()][3];
            for(int i = 0; i < suggestedSongs.length(); i++){
                Song song = new Song(suggestedSongs.getJSONObject(i));
                suggestedSongsList.add(song);
                list[i][0] = String.valueOf(i+1);
                list[i][1] = song.getDisplayName();
                list[i][2] = song.getDurationForDisplay();
            }
        }catch (Exception e){
            logger.error("Get suggested songs:", e);
        }
        return list;
    }

    public String[][] getFriendsSongsForPlaylist(User user, String uid) {
        String[][] list = new String[0][];
        friendsSongsList = new ArrayList<>();
        try{
            P params = new P("uid=" + uid);
            JSONArray friendSongs = make(user, ApiMethods.Audio.GET, params);

            list = new String[friendSongs.length()][3];
            for(int i = 0; i < friendSongs.length(); i++){
                Song song = new Song(friendSongs.getJSONObject(i));
                friendsSongsList.add(song);
                list[i][0] = String.valueOf(i+1);
                list[i][1] = song.getDisplayName();
                list[i][2] = song.getDurationForDisplay();
            }
        }catch (Exception e){
            //TODO: Maybe show popup dialog that user doesn't have music
        }
        return list;
    }

    public String[][] getSongsBySearchForPlaylist(User user, String search) {
        String[][] list = new String[0][];
        searchSongsList = new ArrayList<>();
        try{
            P params = new P("q="+search+",auto_complete=1,sort=2,count=200");
            JSONArray searchedSongs = make(user, ApiMethods.Audio.SEARCH, params);

            list = new String[searchedSongs.length()-1][3];
            for(int i = 1; i < searchedSongs.length(); i++){
                Song song = new Song(searchedSongs.getJSONObject(i));
                searchSongsList.add(song);
                list[i-1][0] = String.valueOf(i);
                list[i-1][1] = song.getDisplayName();
                list[i-1][2] = song.getDurationForDisplay();
            }
        }catch (Exception e){
            logger.error("Get songs by search params:", e);
        }
        return list;
    }

    public void addSongToMyMusic(User user, Song song){
        try {
            P params = new P("aid="+song.getId()+",oid="+ song.getOwner_id());
            Api.make(user.getToken(), ApiMethods.Audio.ADD, params);
        } catch (Exception e) {
            logger.error("Add song to my music:" , e);
        }
    }

    public void deleteSongFromMyMusic(User user, Song song){
        try {
            P params = new P("aid="+song.getId()+",oid="+ song.getOwner_id());
            Api.make(user.getToken(), ApiMethods.Audio.DELETE, params);
        } catch (Exception e) {
            logger.error("Delete song from my music:", e);
        }
    }

    public void editSong(User loggedUser, Song song) {
        try {
            P params = new P("aid=" + song.getId()
                             + ",oid=" + song.getOwner_id()
                             + ",no_search=0"
                             + ",artist=" + song.getArtist()
                             + ",title=" + song.getTitle());
            Api.make(loggedUser.getToken(), ApiMethods.Audio.EDIT, params);
        } catch (Exception e) {
            logger.error("Edit song error:", e);
        }
    }

    public String getLyrics(Song song) {
        if (null == song.getLyricsId())
            return null;

        String result = null;
        User loggedUser = VkPlayerForm.getInstance().getLoggedUser();
        try {
            P params = new P("lyrics_id=" + song.getLyricsId());
            JSONObject jsonResult = new JSONObject(Api.make(loggedUser.getToken(), ApiMethods.Audio.GET_LYRICS, params));
            result = ((JSONObject) jsonResult.get("response")).getString("text");
        } catch (Exception ex) {
            logger.error("Can not get lyrics for song: " + song.getDisplayName(), ex);
        }

        return result;
    }

    public void editSongLyrics(Song song, String text) {
        if (null != song.getLyricsId()) {
            User loggedUser = VkPlayerForm.getInstance().getLoggedUser();
            try {
                P params = new P("aid=" + song.getId()
                        + ",oid=" + song.getOwner_id()
                        + ",no_search=0"
                        + ",artist=" + song.getArtist()
                        + ",title=" + song.getTitle()
                        + ",text=" + text);
                Api.make(loggedUser.getToken(), ApiMethods.Audio.EDIT, params);
            } catch (Exception ex) {
                logger.error("Can not get lyrics for song: " + song.getDisplayName(), ex);
            }
        }
    }

    public List<Song> getMySongsList() {
        return mySongsList;
    }

    public List<Song> getSuggestedSongsList() {
        return suggestedSongsList;
    }

    public List<Song> getFriendsSongsList() {
        return friendsSongsList;
    }

    public List<Song> getSearchSongsList() {
        return searchSongsList;
    }

    // Utils
    private JSONArray make(User user, String method, P params) {
        try {
            JSONObject jsonResult = new JSONObject(Api.make(user.getToken(), method, params));
            return (JSONArray) jsonResult.get("response");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
