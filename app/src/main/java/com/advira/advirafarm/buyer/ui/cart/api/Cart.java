package com.advira.advirafarm.buyer.ui.cart.api;



public class Cart {
    private int id;
    private String title;
    private String shortdesc;
    private String discount;
    private String pack;
    private String moq;
    private double mrp;
    private int image;

    public Cart(int id, String title, String shortdesc, String discount, String pack, String moq, double mrp, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.discount = discount;
        this.pack = pack;
        this.moq = moq;
        this.mrp = mrp;
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

    public String getPack() {
        return pack;
    }

    public String getMoq() {
        return moq;
    }

    public String getDiscount() {
        return discount;
    }

    public double getMrp() {
        return mrp;
    }

    public int getImage() {
        return image;
    }
}