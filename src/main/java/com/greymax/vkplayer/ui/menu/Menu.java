package com.greymax.vkplayer.ui.menu;

import javax.swing.*;


public class Menu extends JMenuBar{

    //TODO: extract name to Constants
    public Menu() {

        // File
        JMenu menuFile = new MenuFile("menu.file");

        // Settings
        JMenu menuSettings = new MenuSettings("menu.settings");

        // Help
        JMenu menuHelp = new MenuHelp("menu.help");

        // Add Menu to MenuBar
        add(menuFile);
        add(menuSettings);
        add(menuHelp);

    }

}
