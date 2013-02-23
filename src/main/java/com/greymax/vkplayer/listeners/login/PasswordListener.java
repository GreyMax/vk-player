package com.greymax.vkplayer.listeners.login;

import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.ui.login.LoginForm;
import com.greymax.vkplayer.ui.custom.JSuggestField;

import javax.swing.*;
import java.awt.event.*;


public class PasswordListener implements FocusListener, KeyListener {
    
    @Override
    public void focusGained(FocusEvent e) {
        String password = Utils.getPasswordForUser(((JSuggestField) e.getOppositeComponent()).getText());
        if(password != "" && password != null) {
            ((JPasswordField) e.getSource()).setText(password);
            ((LoginForm) ((JPasswordField) e.getSource()).getParent()).setSavePasswordChecked(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            ((LoginForm) ((JPasswordField) e.getSource()).getParent()).logIn();
    }

    @Override
    public void focusLost(FocusEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}


}
