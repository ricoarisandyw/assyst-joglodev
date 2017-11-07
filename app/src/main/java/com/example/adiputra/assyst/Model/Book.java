package com.example.adiputra.assyst.Model;

/**
 * Created by Reaper on 11/7/2017.
 */

public class Book {
    public int id;
    public String title;

    public Book(int id, String title){
        this.id = id;
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
