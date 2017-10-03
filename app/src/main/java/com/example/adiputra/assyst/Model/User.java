package com.example.adiputra.assyst.Model;

/**
 * Created by Reaper on 7/16/2017.
 */

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String no_tlp;
    private String token;

    public User(int id, String username, String email, String password, String no_tlp, String token) {
        this.id = id;
        this.no_tlp = no_tlp;
        this.email = email;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setPhone(String phone) {
        this.no_tlp = no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
