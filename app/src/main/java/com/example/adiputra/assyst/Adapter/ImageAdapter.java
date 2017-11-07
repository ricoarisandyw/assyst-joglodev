package com.example.adiputra.assyst.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.adiputra.assyst.Model.Book;
import com.example.adiputra.assyst.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reaper on 11/7/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    List<Book> books = new ArrayList<>();

    public ImageAdapter(Context c,List<Book> books) {
        mContext = c;
        this.books = books;
        mThumbIds = new Integer[books.size()];
        for(int i=0;i<books.size();i++){
            mThumbIds[i] = books.get(i).getId();
        }
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    public Integer[] mThumbIds = {
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
            R.drawable.book1,R.drawable.book2,
    };
}
