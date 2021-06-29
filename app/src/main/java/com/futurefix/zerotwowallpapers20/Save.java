package com.futurefix.zerotwowallpapers20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {

    private Context TheThis;
    FileOutputStream fOut = null;

    public void SaveImage(Context context, Bitmap ImageToSave) {

        TheThis = context;
        String nameOfFolder = "/Download";
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + nameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        String nameOfFile = "Wallpaper";
        File file = new File(dir, nameOfFile + CurrentDateAndTime + ".jpg");

        try {
            fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        } catch(IOException e) {
            UnableToSave();
        }

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                (path, uri) -> {
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        return df.format(c.getTime());
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}