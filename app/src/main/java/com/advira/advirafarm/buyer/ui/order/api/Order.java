package com.advira.advirafarm.buyer.ui.order.api;



public class Order {
    private int id;
    private String title;
    private String shortdesc;
    private int rating;
    private int statusimg;
    private int image;

    public Order(int id, String title, String shortdesc, int rating, int statusimg, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.statusimg = statusimg;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getRating() {
        return rating;
    }

    public int getStatusimg() {
        return statusimg;
    }

    public int getImage() { return image; }
}

