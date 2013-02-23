package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JMenuML extends JMenu {
    
    private String titleKey; 
    
    public JMenuML(String key){
        this.titleKey = key;
    }
    
    public void repaint() {
        setText(Utils.getMessageByKey(titleKey));
        super.repaint();
    }
    
}
