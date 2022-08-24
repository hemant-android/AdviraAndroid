package com.advira.advirafarm.buyer.ui.masterapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileTypeList {

    @SerializedName("id")
    @Expose
    private Integer Id;
    
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileTypeList(Integer Id, String name) {
        this.Id = Id;
        this.name = name;
    }


    @Override
    public String toString() {
        return getName();
    }
}