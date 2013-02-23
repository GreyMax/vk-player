package com.greymax.vkplayer.ui.downloadForm;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.ui.multilanguage.objects.JLabelML;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ProgressListener implements PropertyChangeListener {

    private SingleList singleList;
    private JPanel item;

    public ProgressListener(SingleList singleList, JPanel item) {
        this.singleList = singleList;
        this.item = item;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (item.getComponent(1) instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) item.getComponent(1);
                if ("currentProgress".equals(evt.getPropertyName())) {
                    progressBar.setValue((Integer) evt.getNewValue());
                    if (progressBar.getValue() == 100) {
                        item.remove(1);
                        item.add(getCompleteLabel());
                    }
                    singleList.repaint();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JLabel getCompleteLabel() {
        JLabel label = new JLabelML(Constants.PLAYER.DOWNLOADS.COMPLETE_TEXT);
        label.setBounds(10,30,200,15);

        return label;
    }
}
