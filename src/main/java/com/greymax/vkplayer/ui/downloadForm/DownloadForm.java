package com.greymax.vkplayer.ui.downloadForm;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.ui.multilanguage.objects.JFrameML;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class DownloadForm extends JFrameML {

    private static DownloadForm instance = null;
    SingleList newContentPane = null;

    public static DownloadForm getInstance() {
        if (null == instance)
            instance = new DownloadForm(Constants.PLAYER.DOWNLOADS.WINDOW_TITLE);
        return instance;
    }

    public DownloadForm(String key) {
        super(key);
        setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setTitle(Constants.PLAYER.DOWNLOADS.WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newContentPane = new SingleList();
        newContentPane.setOpaque(true); //content panes must be opaque
        setContentPane(newContentPane);
        setSize(400, 250);
        setLocation(toolkit.getScreenSize().width/2 - 200, toolkit.getScreenSize().height/2 - 125);
    }

    public void addFileToDownload(final File destinationFile, final String name, final String url) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                newContentPane.addNewElement(destinationFile, name, url);
            }
        });
    }

}