package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JRadioButtonMenuItemML extends JRadioButtonMenuItem {

    private String titleKey;

    public JRadioButtonMenuItemML(String key){
        super(Utils.getMessageByKey(key));
        this.titleKey = key;
        repaint();
    }

    public void repaint() {
        setText(Utils.getMessageByKey(titleKey));
        super.repaint();
    }
}
