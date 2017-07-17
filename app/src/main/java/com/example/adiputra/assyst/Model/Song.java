package com.example.adiputra.assyst.Model;

import android.net.Uri;

/**
 * Created by Reaper on 7/16/2017.
 */

public class Song {
    private long id;
    private String title;
    private String artist;
    private Uri uri;
    private String path;

    public Song(long songID, String songTitle, String songArtist, String path) {
        id=songID;
        setTitle(songTitle);
        setArtist(songArtist);
        this.path = path;
    }
    public Song(long songID, String songTitle, String songArtist, Uri uri) {
        id=songID;
        setTitle(songTitle);
        setArtist(songArtist);
        this.uri = uri;
    }
    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
