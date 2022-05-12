package com.project.notesapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {

    public static void saveImageToExternalStorage(Context context, String filename, Bitmap bitmap) throws IOException {

        File file = context.getFilesDir();
        File dir = new File(file.getAbsolutePath()+"/notes");

        System.out.println("Before : "+dir.exists());
        if(!dir.exists())
            dir.mkdirs();
        System.out.println("After : "+dir.exists());
        File image = new File(dir,filename);
        FileOutputStream fileOutputStream = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static Bitmap readImageFromExternalStorage(Context context,String filename) throws IOException {
        File file = context.getFilesDir();
        File dir = new File(file.getAbsolutePath()+"/notes/");
        File image = new File(dir,filename);
        FileInputStream fileInputStream = new FileInputStream(image);
        return BitmapFactory.decodeStream(fileInputStream);
    }
}
