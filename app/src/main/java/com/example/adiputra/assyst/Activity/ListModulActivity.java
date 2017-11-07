package com.example.adiputra.assyst.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.adiputra.assyst.Adapter.ImageAdapter;
import com.example.adiputra.assyst.Model.Book;
import com.example.adiputra.assyst.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListModulActivity extends AppCompatActivity {

    List<Book> tempBooks = new ArrayList<>();
    List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_modul);

        books.add(new Book(R.drawable.book1,"Harry Potter"));
        books.add(new Book(R.drawable.book2,"Princes Hallowen"));
        books.add(new Book(R.drawable.book3,"Gandalf"));
        books.add(new Book(R.drawable.book1,"Beauty and the Beast"));
        books.add(new Book(R.drawable.book2,"The Five Tower"));
        books.add(new Book(R.drawable.book3,"Hunger Games"));
        books.add(new Book(R.drawable.book1,"Book of Solomon"));

        for(Book b :books){
            Log.d("Buku BF", b.title);
            tempBooks.add(b);
        }

        final GridView gridview = (GridView) findViewById(R.id.gv_listmodul_modul);
        final ImageAdapter imageAdapter = new ImageAdapter(this,tempBooks);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(ListModulActivity.this, tempBooks.get(position).title,
                        Toast.LENGTH_SHORT).show();
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.sv_listmodul_modul);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ListModulActivity.this, "Find : "+query,Toast.LENGTH_SHORT).show();
                tempBooks = filterBook(books, query);
                gridview.setAdapter(new ImageAdapter(ListModulActivity.this,tempBooks));
                gridview.invalidateViews();
                for(Book b : tempBooks){
                    Log.d("Buku AF", b.title);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public List<Book> filterBook(List<Book> books, String value){
        List<Book> books1 = new ArrayList<>();
        for(Book b : books){
            if(b.title.toLowerCase().contains(value.toLowerCase())){
                books1.add(b);
            }
        }
        return books1;
    }
}
