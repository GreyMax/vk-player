package com.greymax.vkplayer;

import java.util.Locale;


//TODO: Maybe split this into UI and other constants
public class Constants {

    public static class MENU {

        public static class FILE {
            public static String EXIT_ACTION_COMMAND = "Exit";
            public static String LOGOUT_ACTION_COMMAND = "Logout";
            public static String DOWNLOADS_ACTION_COMMAND = "Downloads";

            public static String EXIT_ITEM_NAME = "menu.file.exit";
            public static String DOWNLOADS_ITEM_NAME = "menu.file.downloads";
        }

        public static class SETTINGS {
            public static String LANGUAGE_EN = "EN";
            public static String LANGUAGE_RU = "RU";
            public static String LANGUAGE_UA = "UA";

            public static final String ENABLE_SONG_POPUP_ACTION_COMMAND = "Enable";
            public static final String DISABLE_SONG_POPUP_ACTION_COMMAND= "Disable";
        }

        public static class HELP {
            public static final String HOT_KEY_ACTION_COMMAND = "HK";
            public static final String ABOUT_ACTION_COMMAND = "About";
        }
    }

    public static class LOCALIZATION {
        public static final Locale LOCALE_EN_US = new Locale("en","US");
        public static final Locale LOCALE_RU_RU = new Locale("ru","RU");
        public static final Locale LOCALE_UA_UA = new Locale("ua","UA");
    }

    public static class PLAYER {

        public static final String PLAYER_LOGIN_TITLE = "player.login.form.title";
        public static final String PLAYER_TITLE = "player.title";

        public static class DOWNLOADS {
            public static final String WINDOW_TITLE = "player.download.window";
            public static final String CLEAR_BUTTON = "player.download.window.clear.button";
            public static final String COMPLETE_TEXT = "player.download.window.complete.text";

            public static final String CLEAR_ACTION_COMMAND = "Clear";
        }

        public static class EQUALIZER {
            public static final String TITLE = "player.equalizer";
        }

        public static class PLAYLIST {
            public static final String MY_MUSIC_TAB_NAME = "player.playlist.tab.my.music";
            public static final String FRIENDS_MUSIC_TAB_NAME = "player.playlist.tab.friends.music";
            public static final String SUGGESTED_MUSIC_TAB_NAME = "player.playlist.tab.suggested.music";
            public static final String SEARCH_TAB_NAME = "player.playlist.tab.search";
            public static final String RADIO_TAB_NAME = "player.playlist.tab.radio";

            public static final String PLAYLIST_TABLE_HEADER_NAME = "player.playlist.header.name";
            public static final String PLAYLIST_TABLE_HEADER_NUMBER = "player.playlist.header.number";
            public static final String PLAYLIST_TABLE_HEADER_TIME = "player.playlist.header.time";
            public static final String PLAYLIST_TABLE_HEADER_RADIO = "player.playlist.header.radio";

            public static final String POPUP_MENU_ADD_TO_MY = "player.playlist.popup.menu.add.to.my";
            public static final String POPUP_MENU_DELETE_FROM_MY = "player.playlist.popup.menu.delete.from.my";
            public static final String POPUP_MENU_DOWNLOAD = "player.playlist.popup.menu.download";
            public static final String POPUP_MENU_POST_TO_WALL = "player.playlist.popup.menu.wall.post";
            public static final String POPUP_MENU_EDIT = "player.playlist.popup.menu.edit";

            public static final int MY_MUSIC_TAB_INDEX = 0;
            public static final int SUGGESTED_MUSIC_TAB_INDEX = 1;
            public static final int FRIENDS_MUSIC_TAB_INDEX = 2;
            public static final int SEARCH_TAB_INDEX = 3;
            public static final int RADIO_TAB_INDEX = 5;

            public static String SEARCH_FIELD_MESSAGE = "player.playlist.tab.search.input.message";
        }

        public static class UPDATE {
            public static final String TITLE = "player.update.dialog.title";
        }
        
        public static class INFORMATION_TITLE {
            public static final int TIMER_DELAY = 3000; // 3 sec
        }

        public static class EDIT_DIALOG {
            public static final String TITLE = "player.edit.dialog.title";
            public static final String ARTIST_LABEL = "player.edit.dialog.artist.label";
            public static final String TITLE_LABEL = "player.edit.dialog.title.label";
        }
    }

    public static class SONG {
        public static final String DURATION = "duration";
        public static final String ARTIST = "artist";
        public static final String TITLE = "title";
        public static final String URL = "url";
    }

}
