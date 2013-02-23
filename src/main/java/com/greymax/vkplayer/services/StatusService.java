package com.greymax.vkplayer.services;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.ApiMethods;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.objects.User;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;


public class StatusService {

    private static final Logger logger = Logger.getLogger(StatusService.class);

    private static StatusService instance = null;

    public static final String TEXT_STATUS = "text";
    public static final String AUDIO_STATUS = "audio";

    private static final String AUDIO_STATUS_PATTERN = AUDIO_STATUS + "=%s_%s";
    private static final String TEXT_STATUS_PATTERN = TEXT_STATUS + "=%s";

    public static StatusService getInstance() {
        if (instance == null)
            instance = new StatusService();

        return instance;
    }
    
    public String getUserStatus(User user) {
        String resultStatus = "";
        try{
            P params = new P();
            JSONObject result = new JSONObject(Api.make(user.getToken(), ApiMethods.Status.GET, params));
            result = result.getJSONObject("response");

            resultStatus = result.getString(TEXT_STATUS);
        } catch (Exception e) {
            logger.error("Get user status: ", e);
        }

        return resultStatus;
    }

    public void setUserStatus(final User user, final String audioId, boolean bNewThread) {
        final P params;
        if (null != audioId) {
            params = new P(String.format(AUDIO_STATUS_PATTERN, user.getToken().getId(), audioId));
        } else {
            params = StringUtils.isEmpty(user.getStatus().trim()) ? new P() : new P(String.format(TEXT_STATUS_PATTERN, user.getStatus().replaceAll(",","&")));
        }

        if (bNewThread)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Api.make(user.getToken(), ApiMethods.Status.SET, params);
                    } catch (Exception e) {
                        logger.error("Set user status: ", e);
                    }
                }
            }).start();

        if (!bNewThread)
            try {
                Api.make(user.getToken(), ApiMethods.Status.SET, params);
            } catch (Exception e) {
                logger.error("Set user status: ", e);
            }
    }
}
