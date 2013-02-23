package com.greymax.vkplayer.ui.custom;

import com.jtattoo.plaf.AbstractLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;


public class ProgressSliderUI extends MetalSliderUI {

    private Icon icon = new ImageIcon(ProgressSliderUI.class.getResource("/resources/img/slider_thumb_violet.png"));

    public ProgressSliderUI() {
        super();
    }

    public ProgressSliderUI(JSlider b) {
    }

    @Override
    public void paintTrack(Graphics g) {
        Color trackColor = new Color(160,32, 255);

        boolean leftToRight = true;

        g.translate(trackRect.x, trackRect.y);

        int trackLeft = 0;
        int trackTop = 0;
        int trackRight = 0;
        int trackBottom = 0;

        // Draw the track
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            trackBottom = (trackRect.height - 1) - getThumbOverhang();
            trackTop = trackBottom - (getTrackWidth() - 1);
            trackRight = trackRect.width - 1;
        }
        else {
            if (leftToRight) {
                trackLeft = (trackRect.width - getThumbOverhang())
                        - getTrackWidth();
                trackRight = (trackRect.width - getThumbOverhang()) - 1;
            }
            else {
                trackLeft = getThumbOverhang();
                trackRight = getThumbOverhang() + getTrackWidth() - 1;
            }
            trackBottom = trackRect.height - 1;
        }

        if (slider.isEnabled()) {
            g.setColor(MetalLookAndFeel.getControlDarkShadow());
            g.drawRect(trackLeft, trackTop, (trackRight - trackLeft) - 1,
                    (trackBottom - trackTop) - 1);

            g.setColor(MetalLookAndFeel.getControlHighlight());
            g.drawLine(trackLeft + 1, trackBottom, trackRight, trackBottom);
            g.drawLine(trackRight, trackTop + 1, trackRight, trackBottom);

            g.setColor(MetalLookAndFeel.getControlShadow());
            g.drawLine(trackLeft + 1, trackTop + 1,
                    trackRight - 2, trackTop + 1);
            g.drawLine(trackLeft + 1, trackTop + 1, trackLeft + 1,
                    trackBottom - 2);
        }
        else {
            g.setColor(MetalLookAndFeel.getControlShadow());
            g.drawRect(trackLeft, trackTop, (trackRight - trackLeft) - 1,
                    (trackBottom - trackTop) - 1);
        }

        // Draw the fill
        if (filledSlider) {
            int middleOfThumb = 0;
            int fillTop = 0;
            int fillLeft = 0;
            int fillBottom = 0;
            int fillRight = 0;

            if (slider.getOrientation() == JSlider.HORIZONTAL) {
                middleOfThumb = thumbRect.x + (thumbRect.width / 2);
                middleOfThumb -= trackRect.x; // To compensate for the
                // g.translate()
                fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
                fillBottom = !slider.isEnabled() ? trackBottom - 1
                        : trackBottom - 2;

                if (!drawInverted()) {
                    fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;

                    // THIS IS THE CHANGE OF NOTE:
                    // Fills the progress with the value from the custom slider
                    fillRight = xPositionForValue(((ProgressSlider) slider)
                            .getProgress());
                    fillRight -= trackRect.x;
                }
                else {
                    fillLeft = middleOfThumb;
                    fillRight = !slider.isEnabled() ? trackRight - 1
                            : trackRight - 2;
                }
            }
            else {
                middleOfThumb = thumbRect.y + (thumbRect.height / 2);
                middleOfThumb -= trackRect.y; // To compensate for the
                // g.translate()
                fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
                fillRight = !slider.isEnabled() ? trackRight - 1
                        : trackRight - 2;

                if (!drawInverted()) {
                    fillTop = middleOfThumb;
                    fillBottom = !slider.isEnabled() ? trackBottom - 1
                            : trackBottom - 2;
                }
                else {
                    fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
                    fillBottom = middleOfThumb;
                }
            }

            if (slider.isEnabled()) {
                g.setColor(slider.getBackground());
                g.drawLine(fillLeft, fillTop, fillRight, fillTop);
                g.drawLine(fillLeft, fillTop, fillLeft, fillBottom);

                g.setColor(trackColor);
                g.fillRect(fillLeft + 1, fillTop + 1, fillRight
                        - fillLeft, fillBottom - fillTop);
            }
            else {
                g.setColor(MetalLookAndFeel.getControlShadow());
                g.fillRect(fillLeft, fillTop, fillRight - fillLeft,
                        trackBottom - trackTop);
            }
        }

        g.translate(-trackRect.x, -trackRect.y);
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


    @Override
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

    protected int getTrackWidth() {
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            return (thumbRect.height - 15);
        } else {
            return (thumbRect.width - 15);
        }
    }

}