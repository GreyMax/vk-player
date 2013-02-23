package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JFrameML extends JFrame {

    private String titleKey;

    public JFrameML(String key){
        this.titleKey = key;
        setIconImage(new ImageIcon(JFrameML.class.getResource("/resources/img/ico.gif")).getImage());
    }

    public void setTitle(String title) {
        this.titleKey = title;
        super.setTitle(Utils.getMessageByKey(titleKey).replaceAll("_"," "));
    }

    public void repaint() {
        setTitle(titleKey);
        super.repaint();
    }
}
