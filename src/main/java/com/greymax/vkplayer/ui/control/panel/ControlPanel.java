package com.greymax.vkplayer.ui.control.panel;

import com.greymax.vkplayer.listeners.control.panel.ControlActionListener;
import com.greymax.vkplayer.listeners.control.panel.SeekListener;
import com.greymax.vkplayer.listeners.control.panel.VolumeListener;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.custom.MyCustomSliderUI;
import com.greymax.vkplayer.ui.custom.ProgressSlider;
import com.greymax.vkplayer.ui.custom.RoundButton;

import javax.swing.*;
import java.awt.*;


public class ControlPanel extends JPanel {

    private ProgressSlider seek;
    private JSlider volume;
    private RoundButton volButton;
    private RoundButton randButton;
    private RoundButton replayButton;
    private boolean animationMode = true;

    public ControlPanel() {
        setLayout(null);
        setBounds(10, 10, 330, 140);
        add(new ControlPanelButtons());
        add(createVolumePanel());
        add(createSeekPanel());
        updateSlidersUI();
        repaint();
    }

    private JPanel createVolumePanel() {
        VolumeListener volumeListener = new VolumeListener();
        volume = new JSlider(JSlider.VERTICAL, 0, 100, 100);
        volume.setPaintTrack(true);
        volume.setPaintLabels(true);
        volume.setFocusable(false);
        volume.setBounds(10, 5, 28, 90);
        volume.addChangeListener(volumeListener);
        volume.addMouseWheelListener(volumeListener);
        volume.addMouseMotionListener(volumeListener);

        volButton = new RoundButton(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/mute_off.png")), "Mute", new ControlActionListener());
        volButton.setBackground(Color.WHITE);
        volButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        volButton.setBounds(8, 96, 27, 27);

        JPanel volPanel = new JPanel(null);
        volPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        volPanel.setBounds(275, 0, 40, 130);
        volPanel.add(volume);
        volPanel.add(volButton);

        return volPanel;
    }

    private JPanel createSeekPanel() {
        randButton = new RoundButton(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/random_off.png")), "Random", new ControlActionListener());
        randButton.setBackground(Color.WHITE);
        randButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        randButton.setBounds(5, 5, 27, 27);

        replayButton = new RoundButton(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/replay_off.png")), "Replay", new ControlActionListener());
        replayButton.setBackground(Color.WHITE);
        replayButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        replayButton.setBounds(40, 5, 27, 27);

        seek = new ProgressSlider();
        seek.setOrientation(JSlider.HORIZONTAL);
        seek.setMinimum(0);
        seek.setMaximum(0);

        seek.setPaintTicks(true);
        seek.setPaintTrack(true);
        seek.setPaintLabels(true);
        seek.setFocusable(false);
        seek.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        seek.setBounds(80, 10, 180, 28);
        SeekListener seekListener = new SeekListener();
        seek.addMouseListener(seekListener);

        JPanel seekPanel = new JPanel(null);
        seekPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        seekPanel.setBounds(0, 90, 275, 40);
        seekPanel.add(seek);
        seekPanel.add(randButton);
        seekPanel.add(replayButton);
        seekPanel.repaint();

        return seekPanel;
    }

    public double getVolumePosition() {
        return new Double(volume.getValue()) / 100;
    }

    public void setVolumePosition(int pos) {
        volume.setValue(pos);
    }

    public void setSeekMaxTime(int maxTime) {
        seek.setMaximum(maxTime);
    }

    public void setCurrentSeekPos(int curPos) {
        if(!seek.getValueIsAdjusting())
            if(animationMode)
                seek.setValue(curPos);
    }

    public void setAnimationMode(boolean mode) {
        animationMode = mode;
    }

    public void setMuteIcon(boolean isMuted) {
        if (isMuted)
            volButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/mute_on.png")));
        else
            volButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/mute_off.png")));
    }

    public void setRandomIcon(boolean isRandom) {
        if (isRandom)
            randButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/random_on.png")));
        else
            randButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/random_off.png")));
    }

    public void setReplayIcon(boolean isReplay) {
        if (isReplay)
            replayButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/replay_on.png")));
        else
            replayButton.setIcon(new ImageIcon(VkPlayerForm.class.getResource("/resources/img/replay_off.png")));
    }

    public void updateSlidersUI() {
        volume.setUI(new MyCustomSliderUI(volume, false));
        volume.setOpaque(false);
        volume.repaint();
    }

    public void updateBufferingProgress(int progress) {
        seek.setProgress(progress);
    }
}
