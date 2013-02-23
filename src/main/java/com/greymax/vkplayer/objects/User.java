package com.greymax.vkplayer.objects;

import com.greymax.vkplayer.auth.Token;
import org.apache.log4j.Logger;
import org.json.JSONObject;


public class User {

    private static Logger logger = Logger.getLogger(User.class);

    private static final String USER_FIST_NAME_PARAMETER = "first_name";
    private static final String USER_LAST_NAME_PARAMETER = "last_name";
    private static final String USER_ID_PARAMETER = "uid";

    private String login;
    private String password;
    private String status;
    private Token token;
    private Settings settings;
    private String firstName;
    private String lastName;
    private Long uid;

    public User(String usr, String pass){
        this.login = usr;
        this.password = pass;
    }

    public User(JSONObject userJson) {
        try {
            this.uid = userJson.getLong(USER_ID_PARAMETER);
            this.firstName = userJson.getString(USER_FIST_NAME_PARAMETER);
            this.lastName = userJson.getString(USER_LAST_NAME_PARAMETER);
        } catch (Exception e) {
            logger.error("Can not create user:", e);
        }
    }

    public Long getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void populateInfo(JSONObject info) {
        try {
            setFirstName((String) info.get(USER_FIST_NAME_PARAMETER));
            setLastName((String) info.get(USER_LAST_NAME_PARAMETER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
