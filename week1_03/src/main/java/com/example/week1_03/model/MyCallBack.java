package com.example.week1_03.model;

public interface MyCallBack<T>{
    /**
     *成功返回的方法
     *@author Administrator
     *@time 2018/12/30 0030 10:00
     */
    void onSuccess(T data);
    /**
     *失败返回
     *@author Administrator
     *@time 2018/12/30 0030 10:00
     */
    void onFail(String error);
}
