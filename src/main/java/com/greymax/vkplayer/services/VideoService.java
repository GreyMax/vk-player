package com.greymax.vkplayer.services;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.ApiMethods;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.objects.Video;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoService {

    private static final Logger logger = Logger.getLogger(VideoService.class);

    private static VideoService instance;
    private List<Video> searchedVideoList;

    public static VideoService getInstance() {
        if (null == instance)
            instance = new VideoService();

        return instance;
    }

    public String[][] getVideoListBySearchKey(User user, String searchKey) {
        String[][] result = new String[0][];
        searchedVideoList = new ArrayList<>();
        try{
            P params = new P("q=" + searchKey);
            String rs = Api.make(user.getToken(), ApiMethods.Video.SEARCH, params);
            JSONObject jsonResult = new JSONObject(rs);
            JSONArray response = (JSONArray) jsonResult.get("response");

            result = new String[response.length()][3];
            for(int i = 0; i < response.length(); i++){
                Video video = new Video(response.getJSONObject(i), true);
                searchedVideoList.add(video);
                result[i][0] = String.valueOf(i+1);
                result[i][1] = video.getDisplayName();
                result[i][2] = video.getDurationForDisplay();
            }
        }catch (Exception e){
            logger.error("Get my songs:", e);
        }

        return result;
    }

    public Video getVideoByUserIdAndVideoId(User user, String ownerId, String videoId) {
        Video result = null;
        try{
            P params = new P("videos=" + ownerId + "_" + videoId);
            String rs = Api.make(user.getToken(), ApiMethods.Video.SEARCH, params);
            JSONObject jsonResult = new JSONObject(rs);
            JSONArray response = (JSONArray) jsonResult.get("response");

            result = new Video(response.getJSONObject(0), false);
        }catch (Exception e){
            logger.error("Get my songs:", e);
        }

        return result;
    }

    public List<Video> getSearchedVideoList() {
        return searchedVideoList;
    }
}
