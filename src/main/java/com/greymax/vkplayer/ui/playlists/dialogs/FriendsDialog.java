package com.greymax.vkplayer.ui.playlists.dialogs;

import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.services.FriendsService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JButtonML;
import com.greymax.vkplayer.ui.multilanguage.objects.JDialogML;
import com.jidesoft.swing.AutoCompletion;
import com.jidesoft.swing.ComboBoxSearchable;
import org.apache.log4j.Logger;
import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class FriendsDialog extends JDialogML implements ActionListener, KeyListener {

    private Logger logger = Logger.getLogger(FriendsDialog.class);
    private static final String OK_COMMAND = "OK";
    private static final String CANCEL_COMMAND = "Cancel";

    public FriendsDialog(JFrame owner, String key) {
        super(owner, key);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250, 110);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(VkPlayerForm.getInstance());
        createDialog();
    }

    private void createDialog() {
        JPanel bgPanel = new JPanel(null);
        bgPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        Vector friends = FriendsService.getInstance().getFriends(VkPlayerForm.getInstance().getLoggedUser());
        JComboBox friendsDropdown = new JComboBox(friends);
        friendsDropdown.setEditor(new MetalComboBoxEditor());
        friendsDropdown.setBounds(10, 10, 220, 25);
        friendsDropdown.setEditable(true);
        friendsDropdown.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        friendsDropdown.getEditor().getEditorComponent().addKeyListener(this);
        ComboBoxSearchable searchable = new ComboBoxSearchable(friendsDropdown);
        AutoCompletion autoCompletion = new AutoCompletion(friendsDropdown, searchable);
        autoCompletion.setStrictCompletion(false);

        JButton btnOk = new JButtonML("player.buttons.ok");
        btnOk.setBounds(50, 45, 50, 25);
        btnOk.setActionCommand(OK_COMMAND);
        btnOk.addActionListener(this);

        JButton btnCancel = new JButtonML("player.buttons.cancel");
        btnCancel.setBounds(120, 45, 70, 25);
        btnCancel.setActionCommand(CANCEL_COMMAND);
        btnCancel.addActionListener(this);

        bgPanel.add(friendsDropdown);
        bgPanel.add(btnOk);
        bgPanel.add(btnCancel);

        add(bgPanel);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(OK_COMMAND)) {
            JComboBox combo = (JComboBox) ((JComponent) e.getSource()).getParent().getComponent(0);
            int index = combo.getSelectedIndex();
            if (index != 0) {
                java.util.List<User> friends = FriendsService.getInstance().getFriendsList();
                User selectedFriend = friends.get(index-1);
                FXPlayerService.getInstance().postSongToWall(selectedFriend);
            }
        }

        this.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JComboBox c = (JComboBox) ((JComponent) e.getSource()).getParent();
            int index = c.getSelectedIndex();
            if (index != 0) {
                java.util.List<User> friends = FriendsService.getInstance().getFriendsList();
                User selectedFriend = friends.get(index-1);
                FXPlayerService.getInstance().postSongToWall(selectedFriend);
                this.dispose();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

}
