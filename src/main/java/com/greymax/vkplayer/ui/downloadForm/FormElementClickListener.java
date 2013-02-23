package com.greymax.vkplayer.ui.downloadForm;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class FormElementClickListener implements MouseListener {

    private static Logger logger = Logger.getLogger(FormElementClickListener.class);

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList) e.getSource();
        if (e.getClickCount() == 2) {
            int index = list.locationToIndex(e.getPoint());
            Desktop desktop;
            // Before more Desktop API is used, first check
            // whether the API is supported by this particular
            // virtual machine (VM) on this particular host.
            if (index > -1)
                if (Desktop.isDesktopSupported()) {
                    try {
                        JComponent c = (JComponent) list.getModel().getElementAt(index);
                        if (c.getComponent(1) instanceof JLabel) {
                            File f = new File(c.getName());
                            desktop = Desktop.getDesktop();
                            desktop.open(f);
                        }
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
