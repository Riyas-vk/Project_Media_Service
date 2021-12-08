package com.example.project_vehicle_service_01;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Common.IMyAidlInterface;

public class MyService extends Service {
 Formatter formatter;
 ServiceInterfaceManager manager;
    static ArrayList<MusicFiles> musicFiles;
    Context mContext;
    MediaPlayer mediaPlayer;

    public MyService() {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();


        Intent intent1 =new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("Service application")
                .setContentText("Application Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        return  START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O);
        {
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
    private final IMyAidlInterface.Stub binder= new IMyAidlInterface.Stub() {
        @Override
        public String getVehicleModel() throws RemoteException {
            return null;
        }

        @Override
        public List<String> getAll() throws RemoteException {
            return myAlpha();
        }

        @Override
        public void playSong() throws RemoteException{
            mediaPlayer.start();
            if(mediaPlayer.isPlaying()){
                System.out.println("playing");
            }
        }

        @Override
        public void pauseSong() throws RemoteException {
            mediaPlayer.pause();
            if(mediaPlayer.isPlaying()){
                System.out.println("Still PLaying");
            }else{
                System.out.println("Paused");
            }
        }


        @Override
        public IBinder asBinder() {
            return super.asBinder();
        }
    };

    public static ArrayList<String> myAlpha() {
        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        return alpha;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    public ServiceInterfaceManager getManager() {
        return manager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Formatter formatter = new Formatter();
        formatter.readfile();
        mediaPlayer = MediaPlayer.create(this,R.raw.cherathukal);

    }

}