package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JTextFieldML extends JTextField {

    private String titleKey = "";

    public JTextFieldML(String titleKey) {
        this.titleKey = titleKey;
    }
    
    public void setText(String text) {
        super.setText(Utils.getMessageByKey(text));
    }

}