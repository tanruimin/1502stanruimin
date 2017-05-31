package com.erzu.dearbaby.category.model.bean;

import java.util.List;

/**
 * Created by 周亚楠
 * on 2017/5/16 -14:36 .
 * Description:
 */

public class Beans extends Object{

    private String text1;
    private List<Object> imgList;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public List<Object> getImgList() {
        return imgList;
    }

    public void setImgList(List<Object> imgList) {
        this.imgList = imgList;
    }
}
