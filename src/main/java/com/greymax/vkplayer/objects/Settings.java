package com.greymax.vkplayer.objects;

import java.util.Locale;


public class Settings {

    private String lookAndFeel;
    private float[] equalizer;
    private Locale locale;
    private Boolean needShowInformationDialog = Boolean.TRUE;
    private int volume;

    public Settings() {
        this.lookAndFeel = "Noire";
        this.equalizer = new float[10];
        this.locale = new Locale("en", "US");
        this.needShowInformationDialog = Boolean.TRUE;
        this.volume = 100;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public float[] getEqualizer() {
        return equalizer;
    }

    public void setEqualizer(float[] equalizer) {
        this.equalizer = equalizer;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    public Boolean getNeedShowInformationDialog() {
        return needShowInformationDialog;
    }

    public void setNeedShowInformationDialog(Boolean needShowInformationDialog) {
        this.needShowInformationDialog = needShowInformationDialog;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getLocaleDisplayName() {
        String result =  "en_US";
        if(getLocale() != null)
            result = getLocale().getLanguage() + "_" + getLocale().getCountry();

        return result;
    }

}
