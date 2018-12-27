package com.example.week1_01.model;

import java.util.Map;

public interface IModel {
    void getRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
