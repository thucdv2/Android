// IMusicService.aidl
package com.example.aidlservice;

// Declare any non-default types here with import statements

interface IMusicService {
    String getSongName();

    void changeMediaStatus();

    void playSong();

    void play();

    void pause();

    int getCurrentDuration();

    int getTotalDuration();
}
