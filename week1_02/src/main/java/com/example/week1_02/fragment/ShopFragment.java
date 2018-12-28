package com.example.week1_02.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week1_02.Apis;
import com.example.week1_02.R;
import com.example.week1_02.ShowActivity;
import com.example.week1_02.bean.ChildBean;
import com.example.week1_02.presenter.IPresenterImpl;
import com.example.week1_02.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopFragment extends Fragment implements IView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    private Unbinder bind;
    private IPresenterImpl iPresenter;
    private ChildBean childBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment, container, false);
        bind = ButterKnife.bind(this, view);
        /*if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        initView();
        initData();
    }
    //@Subscribe(threadMode = ThreadMode.MAIN)
    private void initData() {
        Intent intent = getActivity().getIntent();
        int pid = intent.getIntExtra("pid", 0);
        Map<String,String> params = new HashMap<>();
        params.put("pid",String.valueOf(pid));
        iPresenter.getRequeryData(Apis.URl_DATA_DETAILS,params,ChildBean.class);
    }

    private void initView() {
        //设置banner样式（）
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {
            @Override
            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
                String s = (String) path;
                Uri uri = Uri.parse(s);
                imageView.setImageURI(uri);
            }

            @Override
            public SimpleDraweeView createImageView(Context context) {
                SimpleDraweeView imageView = new SimpleDraweeView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind!=null) {
            bind.unbind();
        }
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ChildBean){
            childBean = (ChildBean) o;
            if (childBean ==null||!childBean.isSuccess()){
                Toast.makeText(getActivity(), childBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                title.setText(childBean.getData().getTitle());
                price.setText("价格："+ childBean.getData().getPrice());
                sub(childBean.getData().getImages());
                banner.setImages(image);
                banner.start();
            }
        }
    }
    /**截取字符串是方法*/
    private List<String> image= new ArrayList<>();
    public void sub(String url){
        //获取以“|”为截取的下标位置
        int i = url.indexOf("|");
        if (i>=0){
            String substring = url.substring(0, i);
            image.add(substring);
            sub(url.substring(i+1,url.length()));
        }else{
            image.add(url);
        }
    }
    @OnClick({R.id.title,R.id.price})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.title:
                EventBus.getDefault().postSticky(childBean.getData().getTitle());
                ((ShowActivity)getActivity()).replace(1);
                break;
            case R.id.price:
                EventBus.getDefault().postSticky(childBean.getData().getPrice());
                ((ShowActivity)getActivity()).replace(2);
                break;
        }
    }
}
