package com.example.adiputra.assyst.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configure {
    private int id;
    private float set_latitude;
    private float set_longitude;
    private String lokasi;
    private String alamat;
    private float latitude;
    private float longitude;
    private String message;
    private int radius;
    private String wifi;
    private String bluetooth;
    private String audio;
    private String air_plane;
    private String mobile_data;
    private int flag;

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

    public float getSet_latitude() {
        return set_latitude;
    }

    public void setSet_latitude(float set_latitude) {
        this.set_latitude = set_latitude;
    }

    public float getSet_longitude() {
        return set_longitude;
    }

    public void setSet_longitude(float set_longitude) {
        this.set_longitude = set_longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public class ListLoc {
        public List<Configure> ll;
    }
}
