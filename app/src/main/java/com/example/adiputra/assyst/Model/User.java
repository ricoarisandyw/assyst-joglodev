package com.example.adiputra.assyst.Model;

/**
 * Created by Reaper on 7/16/2017.
 */

public class User {
    private String username;
    private String email;
    private String password;
    private String phone;

    public User(String username, String email, String password, String phone) {
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
