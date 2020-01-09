package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    private final static String TAG = "MusicService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IMusicService.Stub mBinder = new IMusicService.Stub() {
        @Override
        public String getSongName() throws RemoteException {
            Log.d(TAG, "getSongName");
            return "get SongName";
        }

        @Override
        public void changeMediaStatus() throws RemoteException {
            Log.d(TAG, "changeMediaStatus");
        }

        @Override
        public void playSong() throws RemoteException {
            Log.d(TAG, "playSong");
        }

        @Override
        public void play() throws RemoteException {
            Log.d(TAG, "play");
        }

        @Override
        public void pause() throws RemoteException {
            Log.d(TAG, "pause");
        }

        @Override
        public int getCurrentDuration() throws RemoteException {
            Log.d(TAG, "getCurrentDuration");
            return 0;
        }

        @Override
        public int getTotalDuration() throws RemoteException {
            Log.d(TAG, "getTotalDuration");
            return 0;
        }
    };
}
