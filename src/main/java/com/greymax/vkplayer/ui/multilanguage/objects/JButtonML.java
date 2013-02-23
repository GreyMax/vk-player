package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


//TODO: think about create one abstract class or another way!!!
public class JButtonML extends JButton {

    private String titleKey;

    public JButtonML(String key){
        super(Utils.getMessageByKey(key));
        this.titleKey = key;
        repaint();
    }

    public void repaint() {
        setText(Utils.getMessageByKey(titleKey));
        super.repaint();
    }
}
