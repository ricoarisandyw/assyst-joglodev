package com.example.adiputra.assyst.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adiputra on 10/3/2017.
 */

public class Result {
    @SerializedName("result")
    @Expose
    private boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    private User userData;
    private List<Configure> configureData;

    private Result(boolean result, String message){
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public List<Configure> getConfigureData() {
        return configureData;
    }

    public void setConfigureData(List<Configure> configureData) {
        this.configureData = configureData;
    }
}
