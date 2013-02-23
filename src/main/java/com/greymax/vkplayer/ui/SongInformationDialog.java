package com.greymax.vkplayer.ui;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.objects.Song;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongInformationDialog extends JDialog implements ActionListener {

    private static SongInformationDialog instance;
    private JLabel musicInfo;
    private Timer dialogTimer;
    private final FontMetrics fontMetrics;


    public static SongInformationDialog getInstance() {
        if (null == instance)
            instance = new SongInformationDialog();

        return instance;
    }

    SongInformationDialog() {
        musicInfo = new JLabel();
        dialogTimer = new Timer(Constants.PLAYER.INFORMATION_TITLE.TIMER_DELAY, this);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        fontMetrics = musicInfo.getFontMetrics(musicInfo.getFont());
        int toolkitWidth = toolkit.getScreenSize().width;

        JDialog.setDefaultLookAndFeelDecorated(false);
        setUndecorated(true);
        setBounds(0, 0, toolkitWidth, 100);
        setUndecorated(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        AWTUtilities.setWindowOpacity(this, 0.6f);
        setFocusable(false);
        setFocusableWindowState(false);
        removeButtons(this);

        JPanel bgInformationDialog = new JPanel(null);
        bgInformationDialog.setSize(this.getSize());
        bgInformationDialog.setLocation(0,0);

        musicInfo.setSize(this.getSize());
        musicInfo.setLocation(0,0);
        musicInfo.setHorizontalAlignment(JLabel.CENTER);
        musicInfo.setVerticalAlignment(JLabel.TOP);

        bgInformationDialog.add(musicInfo);

        add(bgInformationDialog);
    }

    public void showSong(Song song) {
        boolean needToAddTripleDot = false;
        if(isVisible())
            setVisible(false);

        try {
            String songTitle = song.getDisplayName();
            String songDuration = song.getDurationForDisplay();

            while (fontMetrics.stringWidth(songTitle) > getWidth()/2 - 60) {
                needToAddTripleDot = true;
                songTitle = songTitle.substring(0, songTitle.length() - 5);
            }

            if (needToAddTripleDot)
                songTitle += "...";

            musicInfo.setText("<html>" +
                    "<p align='center'><font size='6' face='Arial' >" + songTitle + "</font></p>" +
                    "<p align='center'><font size='6' face='Arial' >" + songDuration + "</font></p>" +
                    "</html>");

            if (dialogTimer.isRunning())
                dialogTimer.stop();

            setVisible(true);
            dialogTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeButtons(Component c) {
        if(c == null) c = this;

        if (c instanceof AbstractButton){
            String accn = c.getAccessibleContext().getAccessibleName();
            Container p=c.getParent();
            c.getParent().remove(c);
        }
        else if (c instanceof Container){
            Component[] comps = ((Container)c).getComponents();
            for(int i = 0; i<comps.length; ++i)
                removeButtons(comps[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dialogTimer.stop();
    }
}
