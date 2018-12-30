package com.example.week1_03.model;

import java.util.Map;

public interface IModel {
    void getRequestData(String url, Class clazz, MyCallBack callBack);
    void postRequestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
