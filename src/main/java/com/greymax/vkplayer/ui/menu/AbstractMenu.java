package com.greymax.vkplayer.ui.menu;

import com.greymax.vkplayer.ui.multilanguage.objects.JMenuML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class AbstractMenu extends JMenuML implements ActionListener{

    public static Font font = new Font("Verdana", Font.PLAIN, 11);

    abstract void addMenuItems();
    public abstract void actionPerformed(ActionEvent e);

    public AbstractMenu(String name) {
        super(name);
        setFont(font);
        addMenuItems();
    }

}
