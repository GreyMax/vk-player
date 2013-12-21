package com.greymax.vkplayer.ui.playlists.dialogs;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.services.AudioService;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JButtonML;
import com.greymax.vkplayer.ui.multilanguage.objects.JDialogML;
import com.greymax.vkplayer.ui.playlists.Playlist;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SongLyricsDialog extends JDialogML implements ActionListener{

    private static Logger logger = Logger.getLogger(SongLyricsDialog.class);
    private static final String OK_COMMAND = "OK";
    private static final String CANCEL_COMMAND = "Cancel";

    private JTextArea textArea = new JTextArea();

    public SongLyricsDialog(JFrame owner, String titleKey) {
        super(owner, titleKey);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 400);
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

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, this.getWidth() - 20, this.getHeight() - 80);
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String text = AudioService.getInstance().getLyrics(song);
        textArea.setText(text);

        JButton btnOk = new JButtonML("player.buttons.ok");
        btnOk.setBounds(100, 340, 50, 25);
        btnOk.setActionCommand(OK_COMMAND);
        btnOk.addActionListener(this);

        JButton btnCancel = new JButtonML("player.buttons.cancel");
        btnCancel.setBounds(170, 340, 70, 25);
        btnCancel.setActionCommand(CANCEL_COMMAND);
        btnCancel.addActionListener(this);

        bgPanel.add(scrollPane);
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
                if (Constants.PLAYER.PLAYLIST.MY_MUSIC_TAB_INDEX == index) {
                    List<Song> songs = Playlist.getSongsList(tab);
                    Song song = songs.get(index);

                    AudioService.getInstance().editSongLyrics(song, textArea.getText());
                    Playlist.MyMusic.getPlaylist().refresh();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        this.dispose();

    }
}
