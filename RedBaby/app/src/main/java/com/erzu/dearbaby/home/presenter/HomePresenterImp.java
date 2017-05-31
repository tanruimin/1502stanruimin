package com.erzu.dearbaby.home.presenter;

import com.erzu.dearbaby.home.model.utils.GetDataUtils;
import com.erzu.dearbaby.home.model.utils.GetDataUtilsImp;

import java.util.List;




public class HomePresenterImp implements HomePresenter {

    private GetDataUtils utils;

    @Override
    public List<String> setTitle() {
        utils = new GetDataUtilsImp();
        List<String> titles = utils.getTitles("");//放头布局的接口
        return titles;
    }
}
