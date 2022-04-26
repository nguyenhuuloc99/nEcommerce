package com.example.appfood.Model;

public class Cart {
    private int id;
    private String name;
    private long price;
    private String avatar;
    private int count;

    public Cart() {
    }

    public Cart(int id, String name, long price, String avatar, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
