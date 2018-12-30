package com.example.week1_03.model;

import com.example.week1_03.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getRequestData(String url, Class clazz, MyCallBack callBack) {
        RetrofitManager.getmRetrofitManager().get(url).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(callBack != null){
                        callBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack != null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(callBack != null){
                    callBack.onFail(error);
                }
            }
        });
    }

    @Override
    public void postRequestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack) {
        RetrofitManager.getmRetrofitManager().post(url,params).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(callBack != null){
                        callBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack != null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(callBack != null){
                    callBack.onFail(error);
                }
            }
        });
    }
}
