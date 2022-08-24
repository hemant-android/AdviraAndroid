package com.advira.advirafarm.buyer.ui.masterapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterData {

    @SerializedName("states")
    @Expose
    private List<State> states = null;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;
    @SerializedName("otherdocument")
    @Expose
    private List<Otherdocument> otherdocument = null;

    @SerializedName("product_category")
    @Expose
    private List<ProductCategory> productCategory = null;
    @SerializedName("order_cancele_questions")
    @Expose
    private List<OrderCanceleQuestion> orderCanceleQuestions = null;
    @SerializedName("required_document")
    @Expose
    private List<RequiredDocument> requiredDocument = null;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Otherdocument> getOtherdocument() {
        return otherdocument;
    }

    public void setOtherdocument(List<Otherdocument> otherdocument) {
        this.otherdocument = otherdocument;
    }


    public List<ProductCategory> getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(List<ProductCategory> productCategory) {
        this.productCategory = productCategory;
    }

    public List<OrderCanceleQuestion> getOrderCanceleQuestions() {
        return orderCanceleQuestions;
    }

    public void setOrderCanceleQuestions(List<OrderCanceleQuestion> orderCanceleQuestions) {
        this.orderCanceleQuestions = orderCanceleQuestions;
    }

    public List<RequiredDocument> getRequiredDocument() {
        return requiredDocument;
    }

    public void setRequiredDocument(List<RequiredDocument> requiredDocument) {
        this.requiredDocument = requiredDocument;
    }

}