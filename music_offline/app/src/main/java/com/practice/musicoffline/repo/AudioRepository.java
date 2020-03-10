package com.practice.musicoffline.repo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.practice.musicoffline.model.AudioModel;

import java.util.HashMap;

public class AudioRepository {
    private HashMap<Integer, AudioModel> audioModelMap = new HashMap<>();

    public HashMap<Integer, AudioModel> getAudioModelMap() {
        return audioModelMap;
    }

    public void getAllAudioFromDevice(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.ArtistColumns.ARTIST,};
        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";
        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%storage%"}, sortOrder);

        if (c != null) {
            while (c.moveToNext()) {
                Integer id = c.getInt(0);
                String title = c.getString(1);
                Integer duration = c.getInt(2);
                String path = c.getString(3);
                String album = c.getString(4);
                String artist = c.getString(5);

//                String name = path.substring(path.lastIndexOf("/") + 1);

                AudioModel audioModel = new AudioModel(id, title, artist, duration, album, path);

                if (!audioModelMap.containsKey(id)) {
                    audioModelMap.put(id, audioModel);
                }
            }
            c.close();
        }
    }
}
