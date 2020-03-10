package com.practice.musicoffline.model;

public class AudioModel {
    private int id;
    private String name;
    private String artist;
    private String duration;
    private String album;

    public String getPath() {
        return path;
    }

    private String path;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public AudioModel(int _id, String _name, String _artist, int _duration, String _album, String _path) {
        this.id = _id;
        this.name = _name;
        this.artist = _artist;
        this.duration = durationInMinute(_duration);
        this.album = _album;
        this.path = _path;
    }

    private static String durationInMinute(int duration) {
        int durationSecond = duration / 1000;
        int minute = durationSecond / 60;
        int second = durationSecond - minute * 60;
        if (second < 10) {
            return minute + ":0" + second;
        }
        return minute + ":" + second;
    }
}
