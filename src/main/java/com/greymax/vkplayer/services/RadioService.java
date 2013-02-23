package com.greymax.vkplayer.services;

import com.greymax.vkplayer.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class RadioService {

    //TODO: only for save urls
    private final String[] radioListNames = {"Hit FM",
            "Kiss FM",
            "Europa Plus",
            "Gala Radio",
            "Nashe Radio",
            "Radio ROKS",
            "MFM",
            "Autoradio",
            "Russkoe Radio",
            "Radio Alla"};
    private final String[] radioArrayUrl = {"http://online-hitfm.tavrmedia.ua/HitFM",
            "http://stream.kissfm.ua:8000/kiss",
            "http://cast.radiogroup.com.ua:8000/europaplus",
            "http://live.galaradio.com:8000/kiev",
            "http://cast.radiogroup.com.ua:8000/nashe",
            "http://online-radioroks.tavrmedia.ua/RadioROKS",
            "http://urg.adamant.net:8080/online128",
            "http://cast.radiogroup.com.ua:8000/avtoradio",
            "http://online-rusradio.tavrmedia.ua/RusRadio",
            "http://cast.radiogroup.com.ua:8000/radioalla"};
    private List<String> radioListUrls = new ArrayList();
    private JSONArray radioList = new JSONArray();

    public String[][] getArrayRadio() {
        String [][] rows = new String[radioListNames.length][2];
        Collections.addAll(radioListUrls, radioArrayUrl);
        List<JSONObject> jsonObjects = new ArrayList();
        try{
            for(int i = 0; i < radioListNames.length; i++) {
                rows[i][0] = String.valueOf(i+1);
                rows[i][1] = radioListNames[i];
                JSONObject radio = new JSONObject();
                radio.put(Constants.SONG.URL, radioArrayUrl[i]);
                radio.put(Constants.SONG.DURATION, 0);
                jsonObjects.add(i, radio);
            }
            radioList = new JSONArray(jsonObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    public JSONArray getRadioList() {
        return radioList;
    }
}
