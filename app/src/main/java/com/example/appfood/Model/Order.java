package com.example.appfood.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("total_money")
    @Expose
    private String totalMoney;
    @SerializedName("item")
    @Expose
    private List<Item> item = null;

    public Order(String id, String iduser, String address, String phone, String count, String email, String totalMoney, List<Item> item) {
        this.id = id;
        this.iduser = iduser;
        this.address = address;
        this.phone = phone;
        this.count = count;
        this.email = email;
        this.totalMoney = totalMoney;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
