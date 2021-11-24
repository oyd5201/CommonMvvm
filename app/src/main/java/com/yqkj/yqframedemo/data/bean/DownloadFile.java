package com.yqkj.yqframedemo.data.bean;

import java.io.File;

/**
 * Create by oyd at 2021/11/22
 */
public class DownloadFile {

    private int progress;
    private File file;
    private boolean forgive;

    public DownloadFile() {
    }

    public DownloadFile(int progress, File file, boolean forgive) {
        this.progress = progress;
        this.file = file;
        this.forgive = forgive;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isForgive() {
        return forgive;
    }

    public void setForgive(boolean forgive) {
        this.forgive = forgive;
    }
}
