package com.example.project_vehicle_service_01;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Formatter {
    public String readfile() {
        File sdcard = Environment.getExternalStoragePublicDirectory("text");
        File dir = new File(sdcard.getAbsolutePath());
        if (dir.exists()) {
            File file = new File(dir, "vehiclem.txt");
            FileOutputStream os = null;
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                Log.w("suh", e.toString());
                //You'll need to add proper error handling here
            }String read=text.toString();
            Log.i("Car", read);
            return read;

        }else {

        return null;}
    }
}










