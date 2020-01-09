package com.example.ipcclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.example.aidlservice.IMusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MUSIC_ACTION = "com.example.aidlservice.MusicService.BIND";
    private static final String MUSIC_PACKAGE = "com.example.aidlservice";

    private IMusicService mMusicService;
    private boolean mServiceConnected;

    private Button getSongNameBt;
    private Button changeMediaStatusBt;
    private Button getCurrentDurationBt;
    private Button playBt;
    private Button pauseBt;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = IMusicService.Stub.asInterface(service);
            mServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceConnected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSongNameBt = findViewById(R.id.get_song_name);
        changeMediaStatusBt = findViewById(R.id.change_media_status);
        getCurrentDurationBt = findViewById(R.id.get_current_duration);
        playBt = findViewById(R.id.play);
        pauseBt = findViewById(R.id.pause);

        getSongNameBt.setOnClickListener(this);
        changeMediaStatusBt.setOnClickListener(this);
        getCurrentDurationBt.setOnClickListener(this);
        playBt.setOnClickListener(this);
        pauseBt.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(MUSIC_ACTION);
        intent.setPackage(MUSIC_PACKAGE);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (mMusicService == null) return;

        int id = v.getId();
        try {
            switch (id) {
                case R.id.get_song_name:
                    mMusicService.getSongName();
                    break;
                case R.id.change_media_status:
                    mMusicService.changeMediaStatus();
                    break;
                case R.id.get_current_duration:
                    mMusicService.getCurrentDuration();
                    break;
                case R.id.play:
                    mMusicService.play();
                    break;
                case R.id.pause:
                    mMusicService.pause();
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
