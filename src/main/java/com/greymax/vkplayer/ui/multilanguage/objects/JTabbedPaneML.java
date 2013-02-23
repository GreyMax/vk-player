package com.greymax.vkplayer.ui.multilanguage.objects;

import com.greymax.vkplayer.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class JTabbedPaneML extends JTabbedPane {
    
    private List<String> tabNames = new ArrayList<String>();
    
    public JTabbedPaneML() {
        super();
    }
    
    public void addTab(String title, Component component) {
        tabNames.add(title);
        super.addTab(title, component);
    }
    
    public void repaint() {
        for(int i = 0; i < getTabCount(); i++)
            setTitleAt(i, Utils.getMessageByKey(tabNames.get(i)));

        super.repaint();
    }
}
