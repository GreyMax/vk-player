package com.greymax.vkplayer.auth;

import com.greymax.vkplayer.api.Api;
import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.objects.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Auth {

    private static Logger logger = Logger.getLogger(Auth.class);

    private static final String GET_USER_INFO_API = "users.get";

    private static HttpClient httpclient = new DefaultHttpClient() {
        @Override
        protected CookieSpecRegistry createCookieSpecRegistry() {
            CookieSpecRegistry registry = new CookieSpecRegistry();

            registry.register(
                    CookiePolicy.BEST_MATCH,
                    new AcceptAllSpecFactory());
            return registry;
        }
    };

    private static HttpPost post;
    private static HttpResponse response;
    private static String resultResponse;
    private static String nextRequest;

    private static String makeRequest(String nextRequest, boolean isBody){
        post = new HttpPost(nextRequest);
        try {
            response = httpclient.execute(post);
            if (isBody) {
                resultResponse = EntityUtils.toString(response.getEntity());
            } else {
                resultResponse = response.getFirstHeader("location").getValue();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                String responseBody = EntityUtils.toString(response.getEntity());
                nextRequest = responseBody.substring(responseBody.indexOf("action")+8);
                nextRequest = nextRequest.substring(0, nextRequest.indexOf(">")-1);
                makeRequest(nextRequest, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        post.abort();

        return resultResponse;
    }

    /**
     *
     * @param idapp  Your application's identifier
     * @param settings  Access rights requested by your application
     * @param user User which was login
     * @return P object, which defines access to api
     * @throws AuthorizationException
     */
    public static Token logIn(String idapp, String settings, User user) throws AuthorizationException {
        try{

            //First request

            String responseBody = makeRequest("http://oauth.vk.com/authorize?" +
                    "client_id=" + idapp +
                    "&scope=" + settings +
                    "&redirect_uri=http://oauth.vk.com/blank.html" +
                    "&display=wap&response_type=token", true);


            int start_ip_h = responseBody.indexOf("name=\"ip_h\" value=\"") + 19;
            int end_ip_h = responseBody.substring(start_ip_h).indexOf("\"") + start_ip_h;

            int start_to = responseBody.indexOf("name=\"to\" value=\"") + 17;
            int end_to = responseBody.substring(start_to).indexOf("\"") + start_to;

            String ip_h = responseBody.substring(start_ip_h, end_ip_h);
            String to = responseBody.substring(start_to, end_to);

            post = new HttpPost("https://login.vk.com/?act=login&soft=1&utf8=1");
            List<NameValuePair> postform = new ArrayList<NameValuePair>();
            postform.add(new BasicNameValuePair("q", "1"));
            postform.add(new BasicNameValuePair("from_host", "oauth.vk.com"));
            postform.add(new BasicNameValuePair("from_protocol", "http"));
            postform.add(new BasicNameValuePair("ip_h", ip_h));
            postform.add(new BasicNameValuePair("to", to));
            postform.add(new BasicNameValuePair("email", user.getLogin()));
            postform.add(new BasicNameValuePair("pass", user.getPassword()));

            post.setEntity(new UrlEncodedFormEntity(postform, HTTP.UTF_8));
            response = httpclient.execute(post);
            post.abort();

            nextRequest = response.getFirstHeader("location").getValue();

            /**********************************/

            responseBody = makeRequest(nextRequest, false);

            //Get page with allow_perm function or page with access_token
            nextRequest = makeRequest(nextRequest, false);
            nextRequest = makeRequest(nextRequest, false);

            String accessToken = nextRequest.split("&")[0].split("#")[1].split("=")[1];
            String id = nextRequest.split("=")[nextRequest.split("=").length - 1];

            Token token = new Token(id, accessToken);
            user.setToken(token);
            populateUserInfo(user);
            logger.info("User '"+ user +"' was successfully logged in");

            return token;

        } catch (Exception e) {
            logger.error("Error during login user:", e);
            throw new AuthorizationException();
        }

    }

    private static void populateUserInfo(User user) {
        try {
            P params = new P("uids=" + user.getToken().getId());
            JSONObject result = new JSONObject(Api.make(user.getToken(), GET_USER_INFO_API, params));
            JSONArray info = (JSONArray) result.get("response");
            user.populateInfo(info.getJSONObject(0));
        } catch (Exception e) {
            logger.error("Can not take user info:", e);
        }

    }
}