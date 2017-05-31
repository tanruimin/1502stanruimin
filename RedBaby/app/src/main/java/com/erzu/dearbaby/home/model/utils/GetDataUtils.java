package com.erzu.dearbaby.home.model.utils;

import com.erzu.dearbaby.home.model.bean.MainDataBean;

import java.util.List;



public interface GetDataUtils {

    List<String> getTitles(String path);

    MainDataBean getData(String path);

}
