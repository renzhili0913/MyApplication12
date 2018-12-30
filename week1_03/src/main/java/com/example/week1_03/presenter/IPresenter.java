package com.example.week1_03.presenter;

import com.example.week1_03.model.MyCallBack;

import java.util.Map;

public interface IPresenter {
    void getRequestData(String url, Class clazz);
    void postRequestData(String url, Map<String, String> params, Class clazz);
}
