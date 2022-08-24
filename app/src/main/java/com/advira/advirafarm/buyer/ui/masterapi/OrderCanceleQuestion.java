package com.advira.advirafarm.buyer.ui.masterapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCanceleQuestion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}