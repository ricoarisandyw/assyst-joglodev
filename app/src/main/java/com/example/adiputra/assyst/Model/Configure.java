package com.example.adiputra.assyst.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configure {
    //@SerializedName("id")
    private int id;
    //@SerializedName("lokasi")
    private String lokasi;
    //@SerializedName("alamat")
    private String alamat;
    //@SerializedName("latitude")
    private float latitude;
    //@SerializedName("longitude")
    private float longitude;
    //@SerializedName("message")
    private String message;
    //@SerializedName("radius")
    private int radius;
    //@SerializedName("wifi")
    private String wifi;
    //@SerializedName("bluetooth")
    private String bluetooth;
    //@SerializedName("audio")
    private String audio;
    //@SerializedName("air_plane")
    private String air_plane;
    //@SerializedName("mobile_data")
    private String mobile_data;

    public Configure(int id, String lokasi, String alamat, float latitude, float longitude, int radius, String message,
                     String wifi, String bluetooth, String audio, String air_plane, String mobile_data) {
        this.setId(id);
        this.setLokasi(lokasi);
        this.setAlamat(alamat);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setRadius(radius);
        this.setMessage(message);
        this.setWifi(wifi);
        this.setBluetooth(bluetooth);
        this.setAudio(audio);
        this.setAir_plane(air_plane);
        this.setMobile_data(mobile_data);
    }

    public Configure(int id, String lokasi, String alamat, String message, String wifi,
                     String bluetooth, String audio, String air_plane, String mobile_data) {
        this.setId(id);
        this.setLokasi(lokasi);
        this.setAlamat(alamat);
        this.setMessage(message);
        this.setWifi(wifi);
        this.setBluetooth(bluetooth);
        this.setAudio(audio);
        this.setAir_plane(air_plane);
        this.setMobile_data(mobile_data);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getAir_plane() {
        return air_plane;
    }

    public void setAir_plane(String air_plane) {
        this.air_plane = air_plane;
    }

    public String getMobile_data() {
        return mobile_data;
    }

    public void setMobile_data(String mobile_data) {
        this.mobile_data = mobile_data;
    }


    //    public String toString(){
//        return getAlamat()+""+getLokasi();
//    }
//
    public class ListLoc {
        public List<Configure> ll;
    }
}
