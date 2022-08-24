package com.advira.advirafarm.buyer.ui.masterapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListAll {

    @SerializedName("id")
    @Expose
    private Integer Id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("state_id")
    @Expose
    private String state_id;


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


    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public CityListAll(Integer Id, String name,String state_id) {
        this.Id = Id;
        this.name = name;
        this.state_id = state_id;
    }


    @Override
    public String toString() {
        return getName();
    }
}