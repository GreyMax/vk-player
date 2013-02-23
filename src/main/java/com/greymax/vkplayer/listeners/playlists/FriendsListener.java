package com.greymax.vkplayer.listeners.playlists;

import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.services.FriendsService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


public class FriendsListener implements KeyListener, MouseListener {

    private static Logger logger = Logger.getLogger(FriendsListener.class);

    private FriendsService friendsService = FriendsService.getInstance();
    private VkPlayerForm playerForm = VkPlayerForm.getInstance();

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JComboBox c = (JComboBox) ((JComponent) e.getSource()).getParent();
            int index = c.getSelectedIndex();

            try {
                if (index != 0) {
                    List<User> friends = friendsService.getFriendsList();
                    playerForm.getFriendsMusicPlaylist().showMusicForUser(friends.get(index - 1));
                } else {
                    playerForm.getFriendsMusicPlaylist().showMusicForUser(null);
                }

            } catch (Exception ex){
                logger.error(ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JComboBox c = (JComboBox) ((JComponent) e.getSource()).getParent();
        int index = c.getSelectedIndex();

        try {
            if (index != 0) {
                List<User> friends = friendsService.getFriendsList();
                playerForm.getFriendsMusicPlaylist().showMusicForUser(friends.get(index - 1));
            } else {
                playerForm.getFriendsMusicPlaylist().showMusicForUser(null);
            }

        } catch (Exception ex){
            logger.error(ex);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
