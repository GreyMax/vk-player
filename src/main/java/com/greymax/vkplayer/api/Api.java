package com.greymax.vkplayer.api;

import com.greymax.vkplayer.api.params.P;
import com.greymax.vkplayer.auth.Token;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class Api {
    private static final String apiUrl = "https://api.vkontakte.ru/method/";

    private static String apiRequest(String method, String params) throws ParseException, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(apiUrl + method + params);
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     *
     * @param it Token object to access to API
     * @param m  Which API method
     * @param params Params of API request
     * @return Result of API request (JSON)
     * @throws IOException
     */
    public static String make(Token it, String m, P params) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (String i : params.getParamsAsList()) {
            sb.append(i);
            sb.append("&");
        }
        sb.append("access_token=");
        sb.append(it.getToken());
        return apiRequest(m, sb.toString());
    }

}

