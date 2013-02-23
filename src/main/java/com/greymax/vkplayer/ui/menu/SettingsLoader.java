package com.greymax.vkplayer.ui.menu;

import com.greymax.vkplayer.objects.Settings;
import com.greymax.vkplayer.ui.VkPlayerForm;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;


public class SettingsLoader {

    private static Logger logger = Logger.getLogger(SettingsLoader.class);

    public static void loadSettings(JMenuBar menuBar, Settings settings) {

        try {
            // load look and feel
            Component[] items = ((JMenu) menuBar.getMenu(1).getMenuComponent(0)).getMenuComponents();
            for (Component item : items)
                if (item.getName().equals(settings.getLookAndFeel()))
                    ((JRadioButtonMenuItem) item).setSelected(true);

            // load language
            items = ((JMenu) menuBar.getMenu(1).getMenuComponent(1)).getMenuComponents();
            for (Component item : items)
                if (item.getName().equals(settings.getLocaleDisplayName()))
                    ((JRadioButtonMenuItem) item).setSelected(true);

            // load song popup dialog option
            items = ((JMenu) menuBar.getMenu(1).getMenuComponent(2)).getMenuComponents();
            for (Component item : items)
                if (item.getName().equals(settings.getNeedShowInformationDialog() ? "Enable" : "Disable"))
                    ((JRadioButtonMenuItem) item).setSelected(true);

            // load saved volume
            VkPlayerForm.getInstance().getControlPanel().setVolumePosition(settings.getVolume());
        } catch (Exception ex) {
            logger.error("Can not load user settings:", ex);
        }
    }
}
