package com.greymax.vkplayer.ui.menu;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.ui.multilanguage.objects.JMenuItemML;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MenuHelp extends AbstractMenu{

    private JFrame frame;    
                                        
    public MenuHelp(String name) {
        super(name);
    }

    @Override
    void addMenuItems() {
        
        // Hot Keys
        JMenuItem hotKeys = new JMenuItemML("menu.help.hot.keys");
        hotKeys.setActionCommand(Constants.MENU.HELP.HOT_KEY_ACTION_COMMAND);
        hotKeys.addActionListener(this);

        // About
        JMenuItem about = new JMenuItemML("menu.help.about");
        about.setActionCommand(Constants.MENU.HELP.ABOUT_ACTION_COMMAND);
        about.addActionListener(this);

        add(hotKeys);
        add(about);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(Constants.MENU.HELP.HOT_KEY_ACTION_COMMAND))
            JOptionPane.showMessageDialog(frame, Utils.getMessageByKey("menu.help.hot.keys.dialog.message"), Utils.getMessageByKey("menu.help.hot.keys.dialog.title"), JOptionPane.INFORMATION_MESSAGE);

        if (e.getActionCommand().equals(Constants.MENU.HELP.ABOUT_ACTION_COMMAND))
            JOptionPane.showMessageDialog(frame, Utils.getMessageByKey("menu.help.about.dialog.message"), Utils.getMessageByKey("menu.help.about.dialog.title"), JOptionPane.INFORMATION_MESSAGE);
    }

}
