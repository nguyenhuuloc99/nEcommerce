package com.example.appfood.Model;

public class Item {
    private int id_product;
    private int quantity;
    private String avatar;
    private String name;

    public Item(int id_product, int quantity, String avatar, String name) {
        this.id_product = id_product;
        this.quantity = quantity;
        this.avatar = avatar;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
