package com.greymax.vkplayer.ui.menu;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.downloadForm.DownloadForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JMenuItemML;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MenuFile extends AbstractMenu{

    public MenuFile(String name) {
        super(name);
    }

    void addMenuItems() {
        JMenuItem downloadsItem = new JMenuItemML(Constants.MENU.FILE.DOWNLOADS_ITEM_NAME);
        downloadsItem.setFont(font);
        downloadsItem.setActionCommand(Constants.MENU.FILE.DOWNLOADS_ACTION_COMMAND);
        downloadsItem.addActionListener(this);

        JMenuItem exitItem = new JMenuItemML(Constants.MENU.FILE.EXIT_ITEM_NAME);
        exitItem.setFont(font);
        exitItem.setActionCommand(Constants.MENU.FILE.EXIT_ACTION_COMMAND);
        exitItem.addActionListener(this);

        add(downloadsItem);
        add(exitItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(Constants.MENU.FILE.EXIT_ACTION_COMMAND))
            VkPlayerForm.getInstance().exit();

        if(e.getActionCommand().equals(Constants.MENU.FILE.DOWNLOADS_ACTION_COMMAND))
            if (!DownloadForm.getInstance().isVisible())
                DownloadForm.getInstance().setVisible(true);
    }
}
