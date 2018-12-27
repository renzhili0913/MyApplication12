package com.example.week1_01.model;



import com.example.week1_01.utils.ICallBack;
import com.example.week1_01.utils.OkHttpUtils;

import java.util.Map;

public class IModelImpl implements IModel {
    private MyCallBack myCallBack;
    @Override
    public void getRequeryData(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        this.myCallBack=myCallBack;
        //网络请求
        OkHttpUtils.getmInsaner().postEnqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }
}
