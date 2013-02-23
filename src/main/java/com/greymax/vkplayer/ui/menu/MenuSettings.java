package com.greymax.vkplayer.ui.menu;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.listeners.menu.SkinListener;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JMenuML;
import com.greymax.vkplayer.ui.multilanguage.objects.JRadioButtonMenuItemML;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


//TODO: extract name keys to Constants
public class MenuSettings extends AbstractMenu{

    public MenuSettings(String name) {
        super(name);
    }

    @Override
    void addMenuItems() {
        JMenu subMenuSkins = new JMenuML("menu.settings.skins");
        subMenuSkins.setFont(font);

        List<JRadioButtonMenuItem> skins = new ArrayList();
        skins.add(new JRadioButtonMenuItem("Acryl"));
        skins.add(new JRadioButtonMenuItem("Aero"));
        skins.add(new JRadioButtonMenuItem("Aluminium"));
        skins.add(new JRadioButtonMenuItem("Bernstein"));
        skins.add(new JRadioButtonMenuItem("Fast"));
        skins.add(new JRadioButtonMenuItem("Graphite"));
        skins.add(new JRadioButtonMenuItem("HiFi"));
        skins.add(new JRadioButtonMenuItem("Luna"));
        skins.add(new JRadioButtonMenuItem("McWin"));
        skins.add(new JRadioButtonMenuItem("Mint"));
        skins.add(new JRadioButtonMenuItem("Noire"));
        skins.add(new JRadioButtonMenuItem("Smart"));
        skins.add(new JRadioButtonMenuItem("Texture"));

        ButtonGroup skinGroup = new ButtonGroup();
        for(JMenuItem skin : skins) {
            skin.addActionListener(new SkinListener(skin.getText()));
            skin.setName(skin.getText());
            subMenuSkins.add(skin);
            skinGroup.add(skin);
        }

        // Language Sub Menu
        JMenu subMenuLanguage = new JMenuML("menu.settings.languages");
        subMenuLanguage.setFont(font);

        JMenuItem en = new JRadioButtonMenuItemML("menu.settings.languages.english");
        en.setActionCommand(Constants.MENU.SETTINGS.LANGUAGE_EN);
        en.setName("en_US");
        en.addActionListener(this);

        JMenuItem ru = new JRadioButtonMenuItemML("menu.settings.languages.russian");
        ru.setActionCommand(Constants.MENU.SETTINGS.LANGUAGE_RU);
        ru.setName("ru_RU");
        ru.addActionListener(this);

        JMenuItem ua = new JRadioButtonMenuItemML("menu.settings.languages.ukrainian");
        ua.setActionCommand(Constants.MENU.SETTINGS.LANGUAGE_UA);
        ua.setName("ua_UA");
        ua.addActionListener(this);

        ButtonGroup languageGroup = new ButtonGroup();
        languageGroup.add(en);
        languageGroup.add(ru);
        languageGroup.add(ua);
        subMenuLanguage.add(en);
        subMenuLanguage.add(ru);
        subMenuLanguage.add(ua);

        // Information dialog option
        JMenu subMenuInformationDialog = new JMenuML("menu.settings.song.popup");
        subMenuInformationDialog.setFont(font);

        JMenuItem enable = new JRadioButtonMenuItemML("menu.settings.song.popup.enable");
        enable.setActionCommand(Constants.MENU.SETTINGS.ENABLE_SONG_POPUP_ACTION_COMMAND);
        enable.setName("Enable");
        enable.addActionListener(this);

        JMenuItem disable = new JRadioButtonMenuItemML("menu.settings.song.popup.disable");
        disable.setActionCommand(Constants.MENU.SETTINGS.DISABLE_SONG_POPUP_ACTION_COMMAND);
        disable.setName("Disable");
        disable.addActionListener(this);

        ButtonGroup popupGroup = new ButtonGroup();
        popupGroup.add(enable);
        popupGroup.add(disable);
        subMenuInformationDialog.add(enable);
        subMenuInformationDialog.add(disable);

        add(subMenuSkins);
        add(subMenuLanguage);
        add(subMenuInformationDialog);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = VkPlayerForm.getInstance().getLoggedUser();
        if(e.getActionCommand().equals(Constants.MENU.SETTINGS.LANGUAGE_EN))
            user.getSettings().setLocale(Constants.LOCALIZATION.LOCALE_EN_US);

        if(e.getActionCommand().equals(Constants.MENU.SETTINGS.LANGUAGE_RU))
            user.getSettings().setLocale(Constants.LOCALIZATION.LOCALE_RU_RU);

        if(e.getActionCommand().equals(Constants.MENU.SETTINGS.LANGUAGE_UA))
            user.getSettings().setLocale(Constants.LOCALIZATION.LOCALE_UA_UA);

        if(e.getActionCommand().equals(Constants.MENU.SETTINGS.ENABLE_SONG_POPUP_ACTION_COMMAND))
            user.getSettings().setNeedShowInformationDialog(Boolean.TRUE);

        if(e.getActionCommand().equals(Constants.MENU.SETTINGS.DISABLE_SONG_POPUP_ACTION_COMMAND))
            user.getSettings().setNeedShowInformationDialog(Boolean.FALSE);

        Utils.saveSettingsForUser(user);

        SkinListener.updateLookAndFeel(user.getSettings().getLookAndFeel());

    }
}
