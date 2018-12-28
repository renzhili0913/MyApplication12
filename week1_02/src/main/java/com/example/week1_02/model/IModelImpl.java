package com.example.week1_02.model;

import com.example.week1_02.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelImpl implements IModel {
    @Override
    public void getRequeryData(String url, Map<String, String> params, final Class clazz, final MyCallBack myCallBack) {
        Map<String, RequestBody> map = RetrofitManager.getmRetrofitManager().generateRequestBody(params);
        RetrofitManager.getmRetrofitManager().postFormBody(url,map).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if (myCallBack!=null){
                        myCallBack.setData(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (myCallBack!=null){
                        myCallBack.setData(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.setData(error);
                }
            }
        });
    }
}
