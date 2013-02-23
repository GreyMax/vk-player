package com.greymax.vkplayer.ui.downloadForm;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.ui.multilanguage.objects.JButtonML;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;


public class SingleList extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private JButton deleteButton;

    public SingleList() {
        super(new BorderLayout());
        listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        list = new JList();
        list.setCellRenderer(new CustomCellRenderer());
        list.setModel(listModel);
        list.addMouseListener(new FormElementClickListener());

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        deleteButton = new JButtonML(Constants.PLAYER.DOWNLOADS.CLEAR_BUTTON);
        deleteButton.setActionCommand(Constants.PLAYER.DOWNLOADS.CLEAR_ACTION_COMMAND);
        deleteButton.addActionListener(new DeleteListener(this));

        //Create a panel that uses BoxLayout.
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.add(deleteButton);
        pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(listScrollPane, BorderLayout.CENTER);
        add(pane, BorderLayout.PAGE_END);
    }

    public void addNewElement(File destinationFile, String name, String url) {
        JPanel newListElement = createNewListElement(name, destinationFile);

        listModel.insertElementAt(newListElement, 0);
        list.setSelectedIndex(0);
        list.ensureIndexIsVisible(0);

        try{
            CopyTask task = new CopyTask(destinationFile, new URL(url));
            task.addPropertyChangeListener(new ProgressListener(this, newListElement));
            task.execute();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private JPanel createNewListElement(String name, File file) {
        JPanel element = new JPanel(null);
        element.setBorder(BorderFactory.createLineBorder(new Color(160,32, 255)));
        element.setName(file.getAbsolutePath());
        JLabel label = new JLabel(name);
        label.setBounds(10,5,350,20);

        JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        progressBar.setBounds(10,30,350,15);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);

        element.add(label);
        element.add(progressBar);

        return element;
    }

    class DeleteListener implements ActionListener {

        private JPanel panel;

        public DeleteListener(JPanel panel) {
            this.panel = panel;
        }

        public void actionPerformed(ActionEvent e) {
            int i = 0;
            while (listModel.getSize() > i) {
                JComponent c = (JComponent) listModel.get(i);
                if (c.getComponent(1) instanceof JProgressBar)
                    i++;
                else {
                    listModel.remove(i);
                    panel.repaint();
                }
            }
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
    }

}