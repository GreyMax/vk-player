package com.greymax.vkplayer.listeners.control.panel;

import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;


public class VolumeListener extends MouseAdapter implements ChangeListener {
    
    private FXPlayerService playerService = FXPlayerService.getInstance();
    private VkPlayerForm playerForm = VkPlayerForm.getInstance();

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        if (!slider.getValueIsAdjusting()) {
            // Get new value
            double value = new Double(slider.getValue()) / 100;
            playerService.setVolume(value);
            playerService.setIsMuted(false);
            playerForm.getControlPanel().setMuteIcon(false);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        JSlider slider = (JSlider) e.getSource();
        int currentVolume = (new Double(playerForm.getControlPanel().getVolumePosition() * 100).intValue());
        if (notches < 0) {
            if(currentVolume + e.getScrollAmount() < 100)
                playerForm.getControlPanel().setVolumePosition(currentVolume + e.getScrollAmount());
            else
                playerForm.getControlPanel().setVolumePosition(100);
        } else {
            if(currentVolume - e.getScrollAmount() > 0)
                playerForm.getControlPanel().setVolumePosition(currentVolume - e.getScrollAmount());
            else
                playerForm.getControlPanel().setVolumePosition(0);
        }
    }

    public void mouseDragged(MouseEvent e){
        if (e.getY() >= 0 && e.getY() <= 85) {
            playerService.setVolume(new Double(85-e.getY())/100);
        }
    }
}
