package com.greymax.vkplayer.ui.custom;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;

import java.awt.*;


public class Spinner {

    private static Spinner instance;
    private static JXBusyLabel busylabel;

    public static Spinner getInstance() {
        if (null == instance)
            instance = new Spinner();
        return instance;
    }

    public JXPanel getSpinner() {
        JXPanel panel = new JXPanel(null);
        panel.setBounds(0, 0, 300, 192);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(false);
        panel.setAlpha(0.99f);

        busylabel = new JXBusyLabel(new Dimension(38, 38));
        busylabel.getBusyPainter().setBaseColor(Color.GRAY);
        busylabel.getBusyPainter().setBaseColor(new Color(148,0,211));
        busylabel.setBounds((panel.getWidth() - 38)/2, (panel.getHeight())/2 - 38, 38, 38);
        busylabel.setEnabled(true);

        panel.add(busylabel);
        return panel;
    }

    public void start() {
        busylabel.setEnabled(true);
        busylabel.setBusy(true);
    }

    public void stop() {
        busylabel.setBusy(false);
        busylabel.setEnabled(false);
    }

}
