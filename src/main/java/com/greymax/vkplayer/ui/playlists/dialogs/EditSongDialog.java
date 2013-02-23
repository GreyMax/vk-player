package com.greymax.vkplayer.ui.playlists.dialogs;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.services.AudioService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JButtonML;
import com.greymax.vkplayer.ui.multilanguage.objects.JDialogML;
import com.greymax.vkplayer.ui.multilanguage.objects.JLabelML;
import com.greymax.vkplayer.ui.playlists.MyMusicPlaylist;
import com.greymax.vkplayer.ui.playlists.Playlist;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//TODO: extract to constants
public class EditSongDialog extends JDialogML implements ActionListener {

    private static Logger logger = Logger.getLogger(EditSongDialog.class);
    private static final String OK_COMMAND = "OK";
    private static final String CANCEL_COMMAND = "Cancel";

    public EditSongDialog(JFrame owner, String title) {
        super(owner, title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(280, 150);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(VkPlayerForm.getInstance());
        createDialog();
    }

    private void createDialog() {
        int tab = VkPlayerForm.getInstance().getActiveTabIndex();
        int index = Playlist.getSelectedSongIndex(tab);
        List<Song> songs = Playlist.getSongsList(tab);
        Song song = songs.get(index);

        JPanel bgPanel = new JPanel(null);
        bgPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel artistLabel = new JLabelML(Constants.PLAYER.EDIT_DIALOG.ARTIST_LABEL);
        artistLabel.setBounds(5, 20, 80, 20);
        artistLabel.setHorizontalAlignment(JLabel.RIGHT);
        JLabel titleLabel = new JLabelML(Constants.PLAYER.EDIT_DIALOG.TITLE_LABEL);
        titleLabel.setBounds(5, 55, 80, 20);
        titleLabel.setHorizontalAlignment(JLabel.RIGHT);

        JTextField artist = new JTextField();
        artist.setBounds(90, 20, 160, 20);
        artist.setText(song.getArtist());
        JTextField title = new JTextField();
        title.setBounds(90, 55, 160, 20);
        title.setText(song.getTitle());

        JButton btnOk = new JButtonML("player.buttons.ok");
        btnOk.setBounds(70, 90, 50, 25);
        btnOk.setActionCommand(OK_COMMAND);
        btnOk.addActionListener(this);

        JButton btnCancel = new JButtonML("player.buttons.cancel");
        btnCancel.setBounds(140, 90, 70, 25);
        btnCancel.setActionCommand(CANCEL_COMMAND);
        btnCancel.addActionListener(this);

        bgPanel.add(artistLabel);
        bgPanel.add(artist);
        bgPanel.add(titleLabel);
        bgPanel.add(title);
        bgPanel.add(btnOk);
        bgPanel.add(btnCancel);

        add(bgPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(OK_COMMAND)) {
            try {
                int tab = VkPlayerForm.getInstance().getActiveTabIndex();
                int index = Playlist.getSelectedSongIndex(tab);
                List<Song> songs = Playlist.getSongsList(tab);
                Song song = songs.get(index);

                JTextField artistField = (JTextField) ((JComponent) e.getSource()).getParent().getComponent(1);
                JTextField titleField = (JTextField) ((JComponent) e.getSource()).getParent().getComponent(3);
                String artist = artistField.getText();
                String title = titleField.getText();
                if (StringUtils.isNotEmpty(artist) && StringUtils.isNotEmpty(title)) {
                    song.setArtist(artist);
                    song.setTitle(title);
                    AudioService.getInstance().editSong(VkPlayerForm.getInstance().getLoggedUser(), song);
                    ((MyMusicPlaylist) Playlist.MyMusic.getPlaylist()).refresh();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        this.dispose();
    }
}
