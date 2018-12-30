package com.example.week1_03.view;

public interface IView<T> {
    void getDataSuccess(T data);
    void getDataFail(String error);
}
