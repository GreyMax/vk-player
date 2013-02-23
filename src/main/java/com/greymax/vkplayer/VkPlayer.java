package com.greymax.vkplayer;

import com.greymax.vkplayer.ui.VkPlayerForm;
import org.apache.log4j.PropertyConfigurator;
import javax.swing.*;


public class VkPlayer {

    public static void main(String[] args) {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
            // If Nimbus is not available, you can set the GUI to another look and feel.
            try {
                UIManager.setLookAndFeel(
                        UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        PropertyConfigurator.configure(VkPlayer.class.getResource("/resources/log4j.properties"));
        VkPlayerForm.getInstance().init();

    }
}
