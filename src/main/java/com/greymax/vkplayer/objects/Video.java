package com.greymax.vkplayer.objects;

import com.greymax.vkplayer.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Video {

    private static Logger logger = Logger.getLogger(Video.class);

    private Long id;
    private Long owner_id;
    private String title;
    private int duration;
    private String url;
    private String description;

    public Video() {
        // emptiness
    }

    public Video(JSONObject jsonVideo, boolean isSearch) {
        try {
            if (isSearch) {
                this.id = jsonVideo.getLong("id");
                this.owner_id = jsonVideo.getLong("owner_id");
                this.description = jsonVideo.getString("description");
                this.title = jsonVideo.getString("title");
                this.duration = jsonVideo.getInt("duration");
            } else {
                this.url = jsonVideo.getString("link");
                this.id = jsonVideo.getLong("id");
                this.owner_id = jsonVideo.getLong("owner_id");
                this.description = jsonVideo.getString("description");
                this.title = jsonVideo.getString("title");
                this.duration = jsonVideo.getInt("duration");
            }
        } catch (Exception e) {
            logger.error("Can not parse video from json:", e);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return escape(getTitle());
    }

    public String getFullDisplayName() {
        return getDisplayName().length() < 151
                ? getDisplayName() + ".mp4"
                : getDisplayName().substring(0, 150).trim() + ".mp4";
    }


    public String getDurationForDisplay() {
        return Utils.getTimeFromSeconds(getDuration());
    }

    protected String escape(String text) {
        return StringEscapeUtils.unescapeHtml(
                text.replaceAll("[<,>,:,\",/,\\\\,|,?,*]", "_"));
    }

}
