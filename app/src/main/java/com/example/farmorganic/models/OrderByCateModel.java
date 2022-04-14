package com.example.farmorganic.models;

public class OrderByCateModel {
    String imgUrl;
    String cateName;

    public OrderByCateModel() {
    }

    public OrderByCateModel(String imgUrl, String cateName) {
        this.imgUrl = imgUrl;
        this.cateName = cateName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
