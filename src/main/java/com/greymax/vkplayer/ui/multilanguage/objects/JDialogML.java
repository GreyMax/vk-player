package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JDialogML extends JDialog {

    private String titleKey;

    public JDialogML(JFrame owner, String key) {
        super(owner, Utils.getMessageByKey(key));
        this.titleKey = key;
    }

    public void repaint() {
        setTitle(Utils.getMessageByKey(titleKey));
        super.repaint();
    }
}
