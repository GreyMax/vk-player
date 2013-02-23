package com.greymax.vkplayer.listeners.control.panel;

import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.VkPlayerForm;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class SeekListener implements  MouseListener{

    private FXPlayerService playerService = FXPlayerService.getInstance();
    private VkPlayerForm playerForm = VkPlayerForm.getInstance();

    @Override
    public void mouseClicked(MouseEvent e) {
        playerForm.getControlPanel().setAnimationMode(false);
        JSlider slider = (JSlider) e.getSource();
//        Point p = e.getPoint();
//        double percent = p.x / ((double) slider.getWidth());
//        int range = slider.getMaximum() - slider.getMinimum();
//        double newVal = range * percent;
//        int result = (int)(slider.getMinimum() + newVal);
//        playerService.seek(result);
//        slider.setValue(result);
//        slider.repaint();
        playerService.seek(slider.getValue(), e.getX() > slider.getX());
        playerForm.getControlPanel().setAnimationMode(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        playerForm.getControlPanel().setAnimationMode(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JSlider slider = (JSlider) e.getSource();
        playerService.seek(slider.getValue(), e.getX() > slider.getX());
        playerForm.getControlPanel().setAnimationMode(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
