package com.example.week1_01.presenter;

import java.util.Map;

public interface IPresenter {
    void getRequeryData(String url, Map<String, String> params, Class clazz);
}
