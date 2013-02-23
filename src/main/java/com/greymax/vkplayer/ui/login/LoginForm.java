package com.greymax.vkplayer.ui.login;

import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.auth.Auth;
import com.greymax.vkplayer.auth.AuthorizationException;
import com.greymax.vkplayer.listeners.login.PasswordListener;
import com.greymax.vkplayer.objects.Settings;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.custom.JSuggestField;
import com.greymax.vkplayer.ui.custom.Spinner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class LoginForm extends JLayeredPane implements ActionListener, KeyListener {
    
    public static JSuggestField emailField;
    public static JPasswordField passwordField;
    private static final String CLIENT_ID = "3049094";
    private JFrame controllingFrame = new JFrame();
    private JFrame autocomplete = new JFrame();
    private JCheckBox savePasswordChecked;
    private JButton buttonOK;
    
    public LoginForm() {
        JLabel emailLabel = new JLabel("e-mail or phone:");
        JLabel passwordLabel = new JLabel("Password:");

        passwordField = new JPasswordField();
        emailField = new JSuggestField(autocomplete, Utils.getPossibleLogin());
        emailField.setPreferredSuggestSize(new Dimension(200,60));
        emailField.addSelectionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.requestFocus();
            }
        });

        emailLabel.setBounds(50, 15, 200, 15);
        emailField.setBounds(50, 30, 200, 25);
        passwordLabel.setBounds(50, 65, 200, 10);
        passwordField.setBounds(50, 80, 200, 25);
        PasswordListener passwordListener = new PasswordListener();
        passwordField.addFocusListener(passwordListener);
        passwordField.addKeyListener(passwordListener);

        savePasswordChecked = new JCheckBox("Remember me");
        savePasswordChecked.setBounds(50, 110, 150, 20);
        savePasswordChecked.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!((JCheckBox) e.getSource()).isSelected()) {
                    Utils.removePasswordForUser(emailField.getText());
                }
            }
        });

        buttonOK = new JButton("OK");
        JButton buttonExit = new JButton("Exit");

        buttonExit.setBounds(160, 140, 60, 25);
        buttonOK.setBounds(80, 140, 60, 25);

        buttonOK.setActionCommand("OK");
        buttonOK.addActionListener(this);
        buttonOK.addKeyListener(this);
        buttonExit.setActionCommand("Exit");
        buttonExit.addActionListener(this);
        buttonExit.addKeyListener(this);

//        setLayout(null);
        add(emailField, 1, 0);
        add(emailLabel, 1, 0);
        add(passwordField, 1, 0);
        add(passwordLabel, 1, 0);
        add(savePasswordChecked, 1, 0);
        add(buttonOK, 1, 0);
        add(buttonExit, 1, 0);
    }

    public void setSavePasswordChecked(boolean isChecked) {
        savePasswordChecked.setSelected(isChecked);
    }

    public void logIn() {
        buttonOK.doClick();
    }

    private void enableAll() {
        for (Component c : this.getComponents()) {
            if (c instanceof JSuggestField)
                c.setFocusable(true);
            c.setEnabled(true);
        }
    }

    private void disableAll() {
        for (Component c : this.getComponents()) {
            if (c instanceof JSuggestField)
                c.setFocusable(false);
            c.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
//        JLayeredPane loginForm = (JLayeredPane) ((JComponent) e.getSource()).getParent();
        if ("OK".equals(cmd)) { //Process the password.
            disableAll();
            final Spinner spinner = Spinner.getInstance();
            this.add(spinner.getSpinner(), 2, 0);
            this.validate();
            this.repaint();
            spinner.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    char[] passwordValue = passwordField.getPassword();
                    String emailValue = emailField.getText();
                    User user = new User(emailValue, new String(passwordValue));
                    try{
                        Auth.logIn(CLIENT_ID, "audio,status,friends,wall,video", user);
                        Utils.addUserEmailToFile(user.getLogin());
                        if(savePasswordChecked.isSelected()) Utils.savePasswordForUser(user);
                        Settings settings = Utils.getSettingsForUser(user);
                        user.setSettings(settings);
                        VkPlayerForm.getInstance().createPlayerWindow(user);
                    }catch (AuthorizationException authEx) {
                        System.out.println(authEx.getMessage());
                        spinner.stop();
                        controllingFrame.requestFocusInWindow();
                        JOptionPane.showMessageDialog(controllingFrame,
                                "Please check that you have entered your login and password correctly \n" +
                                        "or check internet connection",
                                "Unable to log in",
                                JOptionPane.ERROR_MESSAGE);
                        enableAll();
                    }
                }
            }).start();
        }
        if("Exit".equals(cmd)) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(((JButton) e.getSource()).getActionCommand().equals("OK"))
                logIn();
            if(((JButton) e.getSource()).getActionCommand().equals("Exit"))
                System.exit(0);
        }
    }
}
