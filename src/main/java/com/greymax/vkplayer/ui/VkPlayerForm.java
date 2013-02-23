package com.greymax.vkplayer.ui;

import com.greymax.vkplayer.Constants;
import com.greymax.vkplayer.Utils;
import com.greymax.vkplayer.listeners.*;
import com.greymax.vkplayer.listeners.equalizer.EqualizerListener;
import com.greymax.vkplayer.listeners.hotkey.GlobalHotKeyListener;
import com.greymax.vkplayer.listeners.menu.SkinListener;
import com.greymax.vkplayer.listeners.playlists.FriendsListener;
import com.greymax.vkplayer.listeners.playlists.SearchFieldListener;
import com.greymax.vkplayer.objects.Song;
import com.greymax.vkplayer.objects.User;
import com.greymax.vkplayer.services.FXPlayerService;
import com.greymax.vkplayer.services.FriendsService;
import com.greymax.vkplayer.services.StatusService;
import com.greymax.vkplayer.ui.control.panel.ControlPanel;
import com.greymax.vkplayer.ui.custom.FileChooserFilterMp3;
import com.greymax.vkplayer.objects.GUIProperties;
import com.greymax.vkplayer.ui.custom.RotatedLabel;
import com.greymax.vkplayer.ui.update.UpdateDialogUI;
import com.greymax.vkplayer.ui.downloadForm.DownloadForm;
import com.greymax.vkplayer.ui.equalizer.EqualizerUI;
import com.greymax.vkplayer.ui.login.LoginForm;
import com.greymax.vkplayer.ui.multilanguage.objects.JFrameML;
import com.greymax.vkplayer.ui.multilanguage.objects.JTabbedPaneML;
import com.greymax.vkplayer.ui.multilanguage.objects.JTextFieldML;
import com.greymax.vkplayer.ui.menu.SettingsLoader;
import com.greymax.vkplayer.ui.playlists.FriendsMusicPlaylist;
import com.greymax.vkplayer.ui.playlists.MyMusicPlaylist;
import com.greymax.vkplayer.ui.playlists.Playlist;
import com.greymax.vkplayer.ui.playlists.SearchMusicPlaylist;
import com.jidesoft.swing.AutoCompletion;
import com.jidesoft.swing.ComboBoxSearchable;
import com.tulskiy.keymaster.common.Provider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class VkPlayerForm extends JFrameML {

    private static final Logger logger = Logger.getLogger(VkPlayerForm.class);

    public static GUIProperties guiProps;
    private static JPanel bgPanel;
    private ControlPanel controlPanel;
    private static User user;
    private static JTextField searchField;
    private static JFrame controllingFrame;
    private Provider provider;
    private static JTabbedPane tabbedPane;
    private static JDialog equalizer;
    private static JDialog updateDialog;

    private static FXPlayerService playerService;
    private static StatusService statusService;
    private static FriendsService friendsService;

    private static VkPlayerForm instance;

    public VkPlayerForm(String key) {
        super(key);
    }

    public static VkPlayerForm getInstance() {
        if(null == instance) {
            instance = new VkPlayerForm("");
        }
        return instance;
    }

    public void init() {
        guiProps = new GUIProperties();
        bgPanel = new JPanel(null);

        playerService = FXPlayerService.getInstance();
        statusService = StatusService.getInstance();
        friendsService = FriendsService.getInstance();
        tabbedPane = new JTabbedPaneML();

        JDialog.setDefaultLookAndFeelDecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width / 2 - 150, toolkit.getScreenSize().height / 2 - 150, 300, 210);
        MainWindowListener wl = new MainWindowListener();
        addComponentListener(wl);
        addWindowListener(wl);
        createLoginForm();
    }
    
    private void createLoginForm() {
        setTitle(Constants.PLAYER.PLAYER_LOGIN_TITLE);
        getContentPane().add(new LoginForm());
        setVisible(true);
    }

    public void createPlayerWindow(User usr) {
        try {
            user = usr;
            String user_status = statusService.getUserStatus(user);
            user.setStatus(user_status);
            setVisible(false);
            setTitle(Constants.PLAYER.PLAYER_TITLE);
            getContentPane().removeAll();
            repaint();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            setBounds(toolkit.getScreenSize().width / 2 - 175, toolkit.getScreenSize().height / 2 - 250, 360, 520);
            setLocationRelativeTo(null);
            super.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    playerService.stop();
                    equalizer.setVisible(false);
                    DownloadForm.getInstance().setVisible(false);
                    if (null != updateDialog) updateDialog.setVisible(false);
                    VkPlayerForm.getInstance().setVisible(false);
                    provider.reset();
                    provider.stop();
                    statusService.setUserStatus(user, null, false);
                    user.getSettings().setVolume(new Double(getControlPanel().getVolumePosition() * 100).intValue());
                    Utils.saveSettingsForUser(user);
                    System.exit(0);
                }
            });

            JMenuBar menu = new com.greymax.vkplayer.ui.menu.Menu();
            setJMenuBar(menu);
            controlPanel = new ControlPanel();
            bgPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            bgPanel.setBounds(15, 15, 350, 500);
            bgPanel.add(controlPanel);
            bgPanel.add(createPlaylist());
            bgPanel.add(createEqualizer());
            bgPanel.repaint();
            getContentPane().add(bgPanel);

            SettingsLoader.loadSettings(menu, user.getSettings());
            if (null != getLoggedUser().getSettings().getLookAndFeel() && !StringUtils.EMPTY.equals(getLoggedUser().getSettings().getLookAndFeel()))
                SkinListener.updateLookAndFeel(getLoggedUser().getSettings().getLookAndFeel());
            GlobalHotKeyListener hotKeyListener = new GlobalHotKeyListener();
            provider = hotKeyListener.getProvider();
            setVisible(true);
            checkNewVersion();
        } catch (Exception ex) {
            logger.error("Global exception: ", ex);
        }
    }

    private void checkNewVersion(){
        java.util.List<String> newVersion = Utils.checkNewVersion();
        if(newVersion.size() > 0) {
            updateDialog = new UpdateDialogUI(controllingFrame, Constants.PLAYER.UPDATE.TITLE, newVersion);
        }
    }

    private RotatedLabel createEqualizer() {
        equalizer = EqualizerUI.getInstance();
        EqualizerUI.getInstance().setSlidersPosition(getLoggedUser().getSettings().getEqualizer());
        for(int i = 0; i < getLoggedUser().getSettings().getEqualizer().length; i++) {
            playerService.setEqGainValue(i, getLoggedUser().getSettings().getEqualizer()[i]);
        }

        RotatedLabel eqLabel = new RotatedLabel(Constants.PLAYER.EQUALIZER.TITLE);
        eqLabel.setDirection(RotatedLabel.Direction.VERTICAL_UP);
        eqLabel.setBounds(335, 20, 20, 70);
        eqLabel.setBackground(Color.ORANGE);
        eqLabel.addMouseListener(new EqualizerListener());

        return eqLabel;
    }

    private JPanel createPlaylist() {
        JPanel playListBg = new JPanel(new GridLayout(1,0));
        playListBg.setBounds(10, 150, 320, 310);

        // My music playlist
        JScrollPane scroll = new JScrollPane(Playlist.MyMusic.getPlaylist());

        // Recommended
        JScrollPane suggestedScroll = new JScrollPane(Playlist.SuggestedMusic.getPlaylist());

        // Friends music playlist
        JComboBox friendsDropdown = new JComboBox(friendsService.getFriends(user));
        friendsDropdown.setEditor(new MetalComboBoxEditor());
        friendsDropdown.setBounds(0, 0, 300, 25);
        friendsDropdown.setEditable(true);
        friendsDropdown.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        friendsDropdown.getEditor().getEditorComponent().addKeyListener(new FriendsListener());
        ComboBoxSearchable searchable = new ComboBoxSearchable(friendsDropdown);
        AutoCompletion autoCompletion = new AutoCompletion(friendsDropdown, searchable);
        autoCompletion.setStrictCompletion(false);

        JScrollPane friendsScroll = new JScrollPane(Playlist.FriendsMusic.getPlaylist());
        friendsScroll.setBounds(0, 30, 320, 250);

        JPanel friendsPanel = new JPanel(null);
        friendsPanel.add(friendsDropdown);
        friendsPanel.add(friendsScroll);

        // Search playlist
        searchField = new JTextFieldML(Constants.PLAYER.PLAYLIST.SEARCH_FIELD_MESSAGE);
        searchField.setBounds(0,0,300,25);
        searchField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        searchField.addKeyListener(new SearchFieldListener());
        searchField.addFocusListener(new SearchFieldListener());
        searchField.setText(Constants.PLAYER.PLAYLIST.SEARCH_FIELD_MESSAGE);

        JScrollPane searchScroll = new JScrollPane(Playlist.SearchMusic.getPlaylist());
        searchScroll.setBounds(0,30,320,250);

        JPanel searchPanel = new JPanel(null);
        searchPanel.add(searchField);
        searchPanel.add(searchScroll);

        // Add tabs
        tabbedPane.addTab(Playlist.MyMusic.getTitleKey(), scroll);
        tabbedPane.addTab(Playlist.SuggestedMusic.getTitleKey(), suggestedScroll);
        tabbedPane.addTab(Playlist.FriendsMusic.getTitleKey(), friendsPanel);
        tabbedPane.addTab(Playlist.SearchMusic.getTitleKey(), searchPanel);
        tabbedPane.setSelectedIndex(0);

        playListBg.add(tabbedPane);

        return playListBg;
    }


//    API

    public void saveSong(Song song) {
        File file = new File(song.getFullDisplayName());
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(file);
        fc.setFileFilter(new FileChooserFilterMp3());
        if( fc.showSaveDialog(VkPlayerForm.getInstance()) == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            DownloadForm.getInstance().addFileToDownload(file, song.getFullDisplayName(), song.getUrl());
            if (!DownloadForm.getInstance().isVisible())
                DownloadForm.getInstance().setVisible(true);
        }
        
    }

    public SearchMusicPlaylist getSearchPlaylist() {
        return (SearchMusicPlaylist) Playlist.SearchMusic.getPlaylist();
    }

    public FriendsMusicPlaylist getFriendsMusicPlaylist() {
        return (FriendsMusicPlaylist) Playlist.FriendsMusic.getPlaylist();
    }

    public MyMusicPlaylist getMyMusicPlaylist() {
        return (MyMusicPlaylist) Playlist.MyMusic.getPlaylist();
    }
    
    public int getActiveTabIndex() {
        return tabbedPane.getSelectedIndex();
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public User getLoggedUser() {
        return user;
    }

    public void exit() {
        provider.reset();
        provider.stop();
        Utils.saveSettingsForUser(user);
        System.exit(0);
    }
}
