package com.greymax.vkplayer.ui.equalizer;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.listeners.equalizer.EqualizerListener;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.ui.custom.RoundButton;
import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.custom.MyCustomSliderUI;
import com.greymax.vkplayer.ui.multilanguage.objects.JLabelML;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;


public class EqualizerUI extends JDialog implements ChangeListener, ActionListener{

    private static EqualizerUI instance;
    private static final int GANES_COUNT = 10;
    private static final int EQ_WIDTH = 250;
    private static final int EQ_HEIGHT = 170;

    private List<JSlider> gainsList = new ArrayList();
    private VkPlayerForm playerForm = VkPlayerForm.getInstance();
    private FXPlayerService playerService = FXPlayerService.getInstance();

    public static EqualizerUI getInstance() {
        if(null == instance)
            instance = new EqualizerUI();

        return instance;
    }

    public EqualizerUI() {
        super();
        setSize(EQ_WIDTH, EQ_HEIGHT);
        double posX = playerForm.getLocation().getX() + playerForm.getWidth();
        double posY = playerForm.getLocation().getY();
        setLocation(new Double(posX).intValue(), new Double(posY).intValue());
        setUndecorated(true);
        setFocusableWindowState(false);
        createGains();
        setVisible(false);
    }

    private void createGains() {
        MouseWheelListener wheelListener = new EqualizerListener();

        JPanel eqBackgroung = new JPanel(null);
        eqBackgroung.setBounds(0,0,this.getWidth(),this.getHeight());
        JLabel eqName = new JLabelML(Constants.PLAYER.EQUALIZER.TITLE);
        eqName.setBounds(90, 10, 80, 20);
        eqBackgroung.add(eqName);

        RoundButton btnClose = new RoundButton(new ImageIcon(EqualizerUI.class.getResource("/resources/img/equalizer_close.png")), "Close_Equalizer", this);
        btnClose.setBounds(this.getWidth() - 26, 10, 16, 16);
        eqBackgroung.add(btnClose);

        for (int i = 0; i < GANES_COUNT; i++) {
            gainsList.add(new JSlider(JSlider.VERTICAL, 0, 48, 24));
        }
        for (int i = 0; i < GANES_COUNT; i++) {
            gainsList.get(i).updateUI();
            gainsList.get(i).setBounds(20 + i * 20, 50, 17, 100);
            gainsList.get(i).setName(String.valueOf(i));
            gainsList.get(i).addMouseWheelListener(wheelListener);
            gainsList.get(i).addChangeListener(this);
            eqBackgroung.add(gainsList.get(i));
        }
        updateSlidersUI();
        add(eqBackgroung);
    }

    public void updateSlidersUI() {
        for (int i = 0; i < GANES_COUNT; i++) {
            gainsList.get(i).setUI(new MyCustomSliderUI(gainsList.get(i), false));
            gainsList.get(i).setOpaque(false);
            gainsList.get(i).repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int gain = Integer.parseInt(slider.getName());
        float resultValue = 0.0f;
        if (new Double((slider).getValue() - 24) > 0) {
            resultValue = new Double(new Double((slider).getValue() - 24) / 2).floatValue();
        } else {
            resultValue = new Double((slider).getValue() - 24).floatValue();
        }
        playerService.setEqGainValue(gain, resultValue);
        playerForm.getLoggedUser().getSettings().setEqualizer(playerService.getEqualizer());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Close_Equalizer"))
            EqualizerUI.getInstance().setVisible(false);
    }
    
    public void setSlidersPosition(float[] values) {
        for (int i = 0; i < gainsList.size(); i++) {
            if (values[i] <= 0) {
                gainsList.get(i).setValue(new Double(values[i] + 24).intValue());
            } else {
                gainsList.get(i).setValue(new Double(values[i] * 2 + 24).intValue());
            }
        }
    }
}
