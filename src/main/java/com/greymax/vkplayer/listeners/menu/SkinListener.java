package com.greymax.vkplayer.listeners.menu;

import com.greymax.vkplayer.ui.equalizer.EqualizerUI;
import com.greymax.vkplayer.ui.SongInformationDialog;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.objects.GUIProperties;
import com.greymax.vkplayer.ui.downloadForm.DownloadForm;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SkinListener implements ActionListener{

    private static final String BASE_PACKAGE = "com.jtattoo.plaf.";
    private String skinName;
    
    public SkinListener(String skin) {
        this.skinName = skin;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        updateLookAndFeel(skinName);
        VkPlayerForm.getInstance().getLoggedUser().getSettings().setLookAndFeel(skinName);
        SongInformationDialog.getInstance().removeButtons(null);
    }

    public static void updateLookAndFeel(String lookAndFeel) {
        final String lf = BASE_PACKAGE + lookAndFeel.toLowerCase() + "." + lookAndFeel + "LookAndFeel";
        try {
            GUIProperties guiProps = VkPlayerForm.guiProps;
            // If new look handles the WindowDecorationStyle not in the same manner as the old look
            // we have to reboot our application.

            LookAndFeel oldLAF = UIManager.getLookAndFeel();
            boolean oldDecorated = false;
            if (oldLAF instanceof MetalLookAndFeel) {
                oldDecorated = true;
            }
            if (oldLAF instanceof AbstractLookAndFeel) {
                oldDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }

            // reset to default theme
            if (lf.equals(GUIProperties.PLAF_METAL)) {
                javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
            } else if (lf.equals(GUIProperties.PLAF_ACRYL)) {
                com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_AERO)) {
                com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_ALUMINIUM)) {
                com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_BERNSTEIN)) {
                com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_FAST)) {
                com.jtattoo.plaf.fast.FastLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_GRAPHITE)) {
                com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_HIFI)) {
                com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_LUNA)) {
                com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_MCWIN)) {
                com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_MINT)) {
                com.jtattoo.plaf.mint.MintLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_NOIRE)) {
                com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_SMART)) {
                com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_TEXTURE)) {
                com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme("Default");
            }
            guiProps.setTheme("Default");
            guiProps.setLookAndFeel(lf);
            UIManager.setLookAndFeel(guiProps.getLookAndFeel());

            LookAndFeel newLAF = UIManager.getLookAndFeel();
            boolean newDecorated = false;
            if (newLAF instanceof MetalLookAndFeel) {
                newDecorated = true;
            }
            if (newLAF instanceof AbstractLookAndFeel) {
                newDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }
            if (oldDecorated != newDecorated) {

            } else {
                // Update the application
                if (JTattooUtilities.getJavaVersion() >= 1.6) {
                    Window windows[] = Window.getWindows();
                    for (Window window : windows) {
                        if (window.isDisplayable()) {
                            SwingUtilities.updateComponentTreeUI(window);
                        }
                    }
                } else {
                    Frame frames[] = Frame.getFrames();
                    for (Frame frame : frames) {
                        if (frame.isDisplayable()) {
                            SwingUtilities.updateComponentTreeUI(frame);
                        }
                    }
                }
            }
            EqualizerUI.getInstance().updateSlidersUI();
            VkPlayerForm.getInstance().getControlPanel().updateSlidersUI();
            SwingUtilities.updateComponentTreeUI(DownloadForm.getInstance());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
