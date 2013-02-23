package com.greymax.vkplayer.listeners;

import com.greymax.vkplayer.ui.equalizer.EqualizerUI;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MainWindowListener implements ComponentListener, WindowListener {

    private boolean isEqualizerVisible = false;
    
    @Override
    public void componentMoved(ComponentEvent evt) {
        if(EqualizerUI.getInstance().isVisible()) {
            int nx = evt.getComponent().getX() + evt.getComponent().getWidth();
            int ny = evt.getComponent().getY() + 25;

            EqualizerUI.getInstance().setLocation(nx, ny);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        if (EqualizerUI.getInstance().isVisible()){
            EqualizerUI.getInstance().setVisible(false);
            this.isEqualizerVisible = true;
        } else {
            this.isEqualizerVisible = false;
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if (isEqualizerVisible)
            EqualizerUI.getInstance().setVisible(true);
    }


    @Override
    public void componentResized(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}

}
