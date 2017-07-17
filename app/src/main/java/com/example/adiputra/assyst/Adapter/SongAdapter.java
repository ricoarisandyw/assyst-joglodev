package com.example.adiputra.assyst.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adiputra.assyst.Activity.MenuActivity;
import com.example.adiputra.assyst.Activity.whonumberActivity;
import com.example.adiputra.assyst.Model.Song;
import com.example.adiputra.assyst.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Reaper on 7/16/2017.
 */

public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    MediaPlayer mp = new MediaPlayer();

    public SongAdapter(Context c, ArrayList<Song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //map to song layout
        final LinearLayout songLay = (LinearLayout)songInf.inflate
                (R.layout.song, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        //get song using position
        final Song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        //set position as tag
        songLay.setTag(position);
        songLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), songs.get(position).getTitle()+" telah dipilih", Toast.LENGTH_SHORT).show();
                saveData("currSong",currSong.getTitle(),parent.getContext());
                Intent i = new Intent(parent.getContext(), MenuActivity.class);
                parent.getContext().startActivity(i);
                ((Activity)parent.getContext()).finish();
            }
        });
        return songLay;
    }

    public void saveData(String name, String value, Context context){
        SharedPreferences prefs = context.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }
    public String loadData(String name, Context context){
        SharedPreferences prefs = context.getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
    public void deleteData(Context context){
        SharedPreferences prefs = context.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        Log.d("Hapus Data:", "");
        editor.commit();
    }
}
