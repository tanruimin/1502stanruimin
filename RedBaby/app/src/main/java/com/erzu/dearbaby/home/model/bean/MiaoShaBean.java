package com.erzu.dearbaby.home.model.bean;


public class MiaoShaBean {

    private String picUrl;
    private String linkUrl;
    private int price;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MiaoShaBean{" +
                "picUrl='" + picUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
