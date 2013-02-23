package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JMenuItemML extends JMenuItem {

    private String titleKey;

    public JMenuItemML(String key){
        super(Utils.getMessageByKey(key));
        this.titleKey = key;
        repaint();
    }

    public void repaint() {
        setText(Utils.getMessageByKey(titleKey));
        super.repaint();
    }

}
