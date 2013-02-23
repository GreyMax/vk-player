package com.greymax.vkplayer.services;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.ApiMethods;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.objects.User;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class FriendsService {

    private static final Logger logger = Logger.getLogger(FriendsService.class);

    private static FriendsService instance = null;
    private List<User> friendsList;

    public static FriendsService getInstance() {
        if (null == instance)
            instance = new FriendsService();

        return  instance;
    }

    public Vector getFriends(User user) {
        Vector result = new Vector();
        friendsList = new ArrayList<>();
        result.add("");
        P params = new P("fields=uid&first_name&last_name,order=name");
        try {
            JSONObject jsonResult = new JSONObject(Api.make(user.getToken(), ApiMethods.Friends.GET, params));
            JSONArray friendsJsonArray = (JSONArray) jsonResult.get("response");
            for (int i = 0; i < friendsJsonArray.length(); i++) {
                User friend = new User(friendsJsonArray.getJSONObject(i));
                result.add(friend.toString());
                friendsList.add(friend);
            }
        } catch (Exception e) {
            logger.error("Get friends error:", e);
        }

        return result;
    }

    public List<User> getFriendsList() {
        return friendsList != null ? friendsList : new ArrayList<User>();
    }
}
