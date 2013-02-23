package com.greymax.vkplayer.ui.update;

import com.greymax.vkplayer.ui.VkPlayerForm;
import com.greymax.vkplayer.ui.downloadForm.CopyTask;
import com.greymax.vkplayer.ui.multilanguage.objects.JButtonML;
import com.greymax.vkplayer.ui.multilanguage.objects.JDialogML;
import com.greymax.vkplayer.ui.multilanguage.objects.JLabelML;
import com.greymax.vkplayer.ui.playlists.table.PlaylistTableModel;
import org.jdesktop.swingx.util.OS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.List;


public class UpdateDialogUI extends JDialogML implements ActionListener{
    //TODO: change location titles to Constants
    private static final String SKIP_COMMAND = "SKIP";
    private static final String DOWNLOAD_COMMAND = "DOWNLOAD";
    private JFrame controllingFrame;
    private ProgressDialog progressDialog;
    private static final String WINDOWS_PLAYER_FILE_URL = "http://dl.dropbox.com/u/31483960/VKPlayer/VKPlayer-install.exe";
    private static final String LINUX_PLAYER_FILE_URL = "http://dl.dropbox.com/u/31483960/VKPlayer/VKPlayer-install.rpm";

    public UpdateDialogUI(JFrame owner, String title, List<String> newVersionDetails) {
        super(owner, title);
        VkPlayerForm playerForm = VkPlayerForm.getInstance();
        String version = newVersionDetails.get(0);
        String[] changes = newVersionDetails.get(1).split("; ");
        setSize(300, 200);
        setLocationRelativeTo(playerForm);
        setResizable(false);
        
        JPanel bgPanel = new JPanel(null);
        bgPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        
        JLabel versionLabel = new JLabelML("player.update.dialog.new.version");
        versionLabel.setBounds(30,10,100,20);
        JLabel versionValueLabel = new JLabel(version);
        versionValueLabel.setBounds(120,10,150,20);
        JLabel changesLabel = new JLabelML("player.update.dialog.changes");
        changesLabel.setBounds(30, 35, 100, 20);
        
        String[] cols = {""};
        String[][] rows = new String[changes.length][changes.length];
        for(int i = 0; i < changes.length; i++) {
            rows[i][0] = changes[i];
        }
        
        JTable changesTable = new JTable();
        changesTable.setModel(new PlaylistTableModel(rows, cols));
        changesTable.setShowGrid(false);
        changesTable.getTableHeader().setReorderingAllowed(false);
        changesTable.setColumnSelectionAllowed(false);
        changesTable.setRowSelectionAllowed(false);
        changesTable.setCellSelectionEnabled(false);
        changesTable.setFocusable(false);

        
        JScrollPane scrollPane = new JScrollPane(changesTable);
        scrollPane.setBounds(30, 55, 250, 70);
        
        JButton btnSkip = new JButtonML("player.buttons.skip");
        btnSkip.setBounds(160, 140, 80, 25);
        btnSkip.setActionCommand(SKIP_COMMAND);
        btnSkip.addActionListener(this);
        
        JButton btnDownload = new JButtonML("player.buttons.download");
        btnDownload.setBounds(50, 140, 90, 25);
        btnDownload.setActionCommand(DOWNLOAD_COMMAND);
        btnDownload.addActionListener(this);

        bgPanel.add(btnDownload);
        bgPanel.add(btnSkip);
        bgPanel.add(versionLabel);
        bgPanel.add(versionValueLabel);
        bgPanel.add(changesLabel);
        bgPanel.add(scrollPane);

        add(bgPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SKIP_COMMAND))
            ((JComponent)e.getSource()).getRootPane().getParent().setVisible(false);
        if (e.getActionCommand().equals(DOWNLOAD_COMMAND)) {
            String fileName = "VKPlayer-install";
            String url = null;
            if (OS.isWindows()) {
                fileName += ".exe";
                url = WINDOWS_PLAYER_FILE_URL;
            }
            if (OS.isLinux()) {
                fileName += ".rpm";
                url = LINUX_PLAYER_FILE_URL;
            }
            File file = new File(fileName);
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(file);
            if( fc.showSaveDialog(VkPlayerForm.getInstance()) == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                progressDialog = new ProgressDialog(controllingFrame, true);
                ((JComponent)e.getSource()).getRootPane().getParent().setVisible(false);
                try{
                    CopyTask task = new CopyTask(file, new URL(url));
                    task.addPropertyChangeListener(progressDialog);
                    SwingUtilities.invokeLater(new Runnable() {
    
                        public void run() {
                            progressDialog.setVisible(true);
                        }
                    });
    
                    task.execute();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
