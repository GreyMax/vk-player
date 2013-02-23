package com.greymax.vkplayer.ui.custom;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;


public class MyCustomSliderUI extends BasicSliderUI{

    private Icon icon = new ImageIcon(MyCustomSliderUI.class.getResource("/resources/img/slider_thumb_violet.png"));
    private boolean isProgressSlider = false;

    public MyCustomSliderUI(JSlider b, boolean isProgress) {
        super(b);
        this.isProgressSlider = isProgress;
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Composite composite = g2D.getComposite();
        if (!slider.isEnabled()) {
            g.setColor(AbstractLookAndFeel.getBackgroundColor());
            g.fillRect(thumbRect.x + 1, thumbRect.y + 1, thumbRect.width - 2, thumbRect.height - 2);
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2D.setComposite(alpha);
        }
        icon.paintIcon(null, g, thumbRect.x, thumbRect.y);
        g2D.setComposite(composite);
    }


    public void paintTrack(Graphics g) {
        boolean leftToRight = JTattooUtilities.isLeftToRight(slider);

        g.translate(trackRect.x, trackRect.y);
        int overhang = 7;
        int trackLeft = 0;
        int trackTop = 0;
        int trackRight = 0;
        int trackBottom = 0;

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            trackBottom = (trackRect.height - 1) - overhang;
            trackTop = trackBottom - (getTrackWidth() - 1);
            trackRight = trackRect.width - 1;
        } else {
            if (leftToRight) {
                trackLeft = (trackRect.width - overhang) - getTrackWidth();
                trackRight = (trackRect.width - overhang) - 1;
            } else {
                trackLeft = overhang;
                trackRight = overhang + getTrackWidth() - 1;
            }
            trackBottom = trackRect.height - 1;
        }

        g.setColor(AbstractLookAndFeel.getFrameColor());
        g.drawRect(trackLeft, trackTop, (trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1);

        int middleOfThumb = 0;
        int fillTop = 0;
        int fillLeft = 0;
        int fillBottom = 0;
        int fillRight = 0;

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            middleOfThumb = thumbRect.x + (thumbRect.width / 2);
            middleOfThumb -= trackRect.x;
            fillTop = trackTop + 1;
            fillBottom = trackBottom - 2;

            if (!drawInverted()) {
                fillLeft = trackLeft + 1;
                if (!isProgressSlider)
                    fillRight = middleOfThumb;
                else {
                    fillRight = xPositionForValue(((ProgressSlider) slider)
                            .getProgress());
                    fillRight -= trackRect.x;
                }
            } else {
                fillLeft = middleOfThumb;
                fillRight = trackRight - 2;
            }
            Color colors[] = null;
            if (!JTattooUtilities.isActive(slider)) {
                colors = AbstractLookAndFeel.getTheme().getInActiveColors();
            } else {
                if (slider.isEnabled()) {
                    colors = AbstractLookAndFeel.getTheme().getSliderColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getDisabledColors();
                }
            }
            JTattooUtilities.fillHorGradient(g, colors, fillLeft + 2, fillTop + 2, fillRight - fillLeft - 2, fillBottom - fillTop - 2);
            Color cHi = ColorHelper.darker(colors[colors.length - 1], 5);
            Color cLo = ColorHelper.darker(colors[colors.length - 1], 10);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, fillLeft + 1, fillTop + 1, fillRight - fillLeft - 1, fillBottom - fillTop - 1);
        } else {
            middleOfThumb = thumbRect.y + (thumbRect.height / 2);
            middleOfThumb -= trackRect.y;
            fillLeft = trackLeft + 1;
            fillRight = trackRight - 2;

            if (!drawInverted()) {
                fillTop = middleOfThumb;
                fillBottom = trackBottom - 2;
            } else {
                fillTop = trackTop + 1;
                fillBottom = middleOfThumb;
            }
            Color colors[] = null;
            if (!JTattooUtilities.isActive(slider)) {
                colors = AbstractLookAndFeel.getTheme().getInActiveColors();
            } else {
                if (slider.isEnabled()) {
                    colors = AbstractLookAndFeel.getTheme().getSliderColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getDisabledColors();
                }
            }
            JTattooUtilities.fillVerGradient(g, colors, fillLeft + 2, fillTop + 2, fillRight - fillLeft - 2, fillBottom - fillTop - 2);
            Color cHi = ColorHelper.darker(colors[colors.length - 1], 5);
            Color cLo = ColorHelper.darker(colors[colors.length - 1], 10);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, fillLeft + 1, fillTop + 1, fillRight - fillLeft - 1, fillBottom - fillTop - 1);
        }
        g.translate(-trackRect.x, -trackRect.y);
    }

    protected int getTrackWidth() {
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            return (thumbRect.height - 15);
        } else {
            return (thumbRect.width - 15);
        }
    }

    protected Dimension getThumbSize() {
        Dimension size = super.getThumbSize();
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            size.width = icon.getIconWidth();
            size.height = icon.getIconHeight();
        } else {
            size.width = icon.getIconWidth();
            size.height = icon.getIconHeight();
        }

        return size;
    }

    protected void scrollDueToClickInTrack(int direction) {
        // this is the default behaviour, let's comment that out
        // scrollByBlock(direction);
        int value = slider.getValue();

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            value = this.valueForXPosition(slider.getMousePosition().x);
        } else if (slider.getOrientation() == JSlider.VERTICAL) {
            value = this.valueForYPosition(slider.getMousePosition().y);
        }
        slider.setValue(value);
    }

}
