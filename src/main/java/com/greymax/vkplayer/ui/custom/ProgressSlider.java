package com.greymax.vkplayer.ui.custom;

import javax.swing.*;


public class ProgressSlider extends JSlider {
    protected int progress;
//    private static final String uiClassID = "ProgressSliderUI";
    private static final String uiClassID = "MyCustomSliderUI";

    public ProgressSlider() {
        progress = 0;
        putClientProperty("JSlider.isFilled", Boolean.TRUE);
        updateUI();
        setOpaque(false);
    }

    public void updateUI() {
        setUI(new MyCustomSliderUI(this, true));
    }

    public String getUIClassID() {
        return uiClassID;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int value) {
        if (value < this.getMinimum()) {
            this.progress = this.getMinimum();
        }
        else if (value > this.getMaximum()) {
            this.progress = this.getMaximum();
        }
        else {
            this.progress = value;
        }
    }
}
