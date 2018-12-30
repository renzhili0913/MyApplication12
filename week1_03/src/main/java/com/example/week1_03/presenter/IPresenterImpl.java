package com.example.week1_03.presenter;

import com.example.week1_03.model.IModelImpl;
import com.example.week1_03.model.MyCallBack;
import com.example.week1_03.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequestData(String url, Class clazz) {
        iModel.getRequestData(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    @Override
    public void postRequestData(String url, Map<String, String> params, Class clazz) {
        iModel.postRequestData(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }
    public void onDetach() {
        if (iModel != null) {
            iModel = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
