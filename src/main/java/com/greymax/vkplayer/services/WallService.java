package com.greymax.vkplayer.services;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.ApiMethods;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.auth.Token;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.objects.User;
import org.apache.log4j.Logger;

public class WallService {

    private static final Logger logger = Logger.getLogger(WallService.class);

    private static final String OWNER_PARAMETER = "owner_id";
    private static final String ATTACHMENT_PARAMETER = "attachments";
    private static final String AUDIO_ATTACHMENT_PATTERN = "audio%s_%s";

    private static WallService instance = null;

    public static WallService getInstance() {
        if  (null == instance)
            instance = new WallService();

        return instance;
    }

    public void postAudio(final Token it, final User usr, final Song song) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    P params = new P(OWNER_PARAMETER + "=" + usr.getUid() + ","
                            + ATTACHMENT_PARAMETER + "=" + getAttachment(song.getOwner_id(), song.getId()));
                    Api.make(it, ApiMethods.Wall.POST, params);
                } catch (Exception e) {
                    logger.error("Can not post audio:", e);
                }
            }
        }).start();
    }

    private String getAttachment(Long ownerId, Long mediaId) {
        return String.format(AUDIO_ATTACHMENT_PATTERN,
                String.valueOf(ownerId.intValue()),
                String.valueOf(mediaId.intValue()));
    }
}
