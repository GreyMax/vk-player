package com.greymax.vkplayer.ui.downloadForm;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class CopyTask extends SwingWorker<Void, Void> {

    private File target;
    private URL[] urls;
    private long overall;
    private long currentOverall;
    private int currentProgress;
    private URL currentFrom;
    private File currentTo;

    public CopyTask(File targetDir, URL url){
        this.target = targetDir;
        urls = new URL[] {url};
        overall = 0;
        currentOverall = 0;
    }

    @Override
    protected Void doInBackground() throws Exception {
        setProgress(0);
        overall += urls[0].openConnection().getContentLength();

        for(int k = 0;k < urls.length;k++){
            firePropertyChange("currentFrom", currentFrom, urls[k]);
            currentFrom = urls[k];
            firePropertyChange("currentTo", currentTo, target);
            currentTo = target;
            doCopy(urls[k], target);
        }

        return null;
    }

    private void doCopy(URL url, File target) throws IOException {
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        long total = conn.getContentLength();
        currentProgress = 0;

        OutputStream fout = new BufferedOutputStream(new FileOutputStream(target));
        byte[] buffer = new byte[1024];
        long read = 0;
        int count = 0;

        while((count = in.read(buffer)) != -1){
            fout.write(buffer, 0, count);
            read += count;
            currentOverall += count;
            setProgress((int)(100 * currentOverall/overall));
            firePropertyChange("currentProgress", currentProgress, (int)(100 * read/total));
            currentProgress = (int)(100 * read/total);
        }
        in.close();
        fout.close();
    }

}
