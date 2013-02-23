package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;


public class JLabelML extends JLabel {

    private String titleKey;

    public JLabelML(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public JLabelML(Icon image) {
        super(image);
    }

    public JLabelML() {
        super();
    }

    public JLabelML(String key, Icon icon, int horizontalAlignment) {
        super(Utils.getMessageByKey(key), icon, horizontalAlignment);
    }

    public JLabelML(String key, int horizontalAlignment) {
        super(Utils.getMessageByKey(key), horizontalAlignment);
    }

    public JLabelML(String key){
        super(Utils.getMessageByKey(key));
        this.titleKey = key;
        repaint();
    }

    public void repaint() {
        setText(Utils.getMessageByKey(titleKey));
        super.repaint();
    }
}
