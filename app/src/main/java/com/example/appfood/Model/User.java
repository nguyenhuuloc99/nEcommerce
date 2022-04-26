package com.example.appfood.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("avatar")
    @Expose
    private String avatar;


    public User() {
    }

    public User(String email, String fullName, String pass, String avatar) {
        this.email = email;
        this.fullName = fullName;
        this.pass = pass;
        this.avatar = avatar;
    }

    public User(int id, String full_name, String email, String password, String avatar) {
        this.id = id;
        this.fullName = full_name;
        this.email = email;
        this.pass = password;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return fullName;
    }

    public void setFull_name(String full_name) {
        this.fullName = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
