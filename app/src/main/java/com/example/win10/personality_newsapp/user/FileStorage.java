package com.example.win10.personality_newsapp.user;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

/**
 * Created by Fsh on 2016/12/28.
 */

public class FileStorage {
    public File cropIconDir;
    private File iconDir;
   public  File  out_Dir=new File( new File(Environment.getExternalStorageDirectory(), "/" + "demo" + "/crop"), "out" + ".jpg");

    public FileStorage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File external = Environment.getExternalStorageDirectory();
            String rootDir = "/" + "demo";
            cropIconDir = new File(external, rootDir + "/crop");
            if (!cropIconDir.exists()) {
                cropIconDir.mkdirs();

            }
            iconDir = new File(external, rootDir + "/icon");
            if (!iconDir.exists()) {
                iconDir.mkdirs();

            }
        }
    }

    public File createCropFile() {
        String fileName = "";
        if (cropIconDir != null) {
            fileName = "out" + ".jpg";
        }
        return new File(cropIconDir, fileName);
    }

    public File createIconFile() {
        String fileName = "";
        if (iconDir != null) {
            fileName = UUID.randomUUID().toString() + ".png";
        }
        return new File(iconDir, fileName);
    }

}
