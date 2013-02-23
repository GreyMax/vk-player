package com.greymax.vkplayer.objects;

import com.greymax.vkplayer.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Song {

    private static Logger logger = Logger.getLogger(Song.class);

    private Long id;
    private Long owner_id;
    private String title;
    private int duration;
    private String url;
    private String artist;

    public Song() {
        // emptiness
    }

    public Song(JSONObject jsonSong) {
        try {
            this.id = jsonSong.getLong("aid");
            this.url = jsonSong.getString("url");
            this.owner_id = jsonSong.getLong("owner_id");
            this.artist = jsonSong.getString("artist");
            this.title = jsonSong.getString("title");
            this.duration = jsonSong.getInt("duration");
        } catch (Exception e) {
            logger.error("Can not parse song from json:", e);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayName() {
        return escape(getArtist() + " - " + getTitle());
    }

    public String getFullDisplayName() {
        return getDisplayName().length() < 151
                ? getDisplayName() + ".mp3"
                : getDisplayName().substring(0, 150).trim() + ".mp3";
    }

    public String getDurationForDisplay() {
        return Utils.getTimeFromSeconds(getDuration());
    }

    protected String escape(String text) {
        return StringEscapeUtils.unescapeHtml(
                text.replaceAll("[<,>,:,\",/,\\\\,|,?,*]", "_"));
    }
}
