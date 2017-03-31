/*
 * Copyright (c) www.bugull.com
 */

package com.pengjf.myapp.utils;

import android.os.Environment;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 *
 * @author Frank Wen(xbwen@hotmail.com)
 */
public class FileStorage {
    private File dataDir;
    private File tempDir;
    private File iconDir;
    
    public FileStorage(){
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File external = Environment.getExternalStorageDirectory();

            String rootDir = "/" + "fuhuishun";

            tempDir = new File(external, rootDir + "/temp");
            if(! tempDir.exists()){
                tempDir.mkdirs();
            }

            iconDir = new File(external, rootDir + "/icon");
            if(! iconDir.exists()){
                iconDir.mkdirs();
            }

            dataDir = new File(external,rootDir + "/data");
            if(!dataDir.exists())
                dataDir.mkdirs();
        }
    }

    public File createTempFile(){
        if(tempDir == null){
            return null;
        }
        String filename = System.currentTimeMillis() + ".png";
        return new File(tempDir, filename);
    }

    public File getTempDir() {
        return tempDir;
    }

    public File getIconDir() {
        return iconDir;
    }

    public File getDataDir(){
        return dataDir;
    }

}
