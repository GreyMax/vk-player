package com.greymax.vkplayer.ui.custom;

import javax.swing.filechooser.FileFilter;
import java.io.File;


public class FileChooserFilterMp3 extends FileFilter {

    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".mp3");
    }

    public String getDescription() {
        return "*.mp3";
    }

}
