package com.greymax.vkplayer.listeners.equalizer;

import com.greymax.vkplayer.ui.equalizer.EqualizerUI;
import com.greymax.vkplayer.ui.VkPlayerForm;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class EqualizerListener implements MouseListener, MouseWheelListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        EqualizerUI equalizerUI = EqualizerUI.getInstance();
        if(equalizerUI.isVisible()) {
            equalizerUI.setVisible(false);
        } else {
            VkPlayerForm playerForm = VkPlayerForm.getInstance();
            double posX = playerForm.getLocation().getX() + playerForm.getWidth();
            double posY = playerForm.getLocation().getY() + 25;
            equalizerUI.setLocation(new Double(posX).intValue(), new Double(posY).intValue());
            equalizerUI.setVisible(true);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        JSlider slider = (JSlider) e.getSource();
        int currentState = slider.getValue();
        if (notches < 0) {
            if(currentState + e.getScrollAmount() < 200) {
                slider.setValue(currentState + e.getScrollAmount());
            } else {
                slider.setValue(200);
            }
        } else {
            if(currentState - e.getScrollAmount() > 0) {
                slider.setValue(currentState - e.getScrollAmount());
            } else {
                slider.setValue(0);
            }
        }
    }
}
