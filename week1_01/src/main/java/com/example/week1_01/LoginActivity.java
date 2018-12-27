package com.example.week1_01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.week1_01.bean.ChildBean;
import com.example.week1_01.bean.ShopBean;
import com.example.week1_01.presenter.IPresenterImpl;
import com.example.week1_01.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements IView {
    private IPresenterImpl  iPresenter;
    @BindView(R.id.image_haned)
    SimpleDraweeView image_haned;
    @BindView(R.id.xbanner)
    XBanner xBanner;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @OnClick(R.id.but)
    public void sign(){
        login();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_item);
        iPresenter=new IPresenterImpl(this);
        initView();
        initData();
    }
    //登录
    private void login() {
        //获得UMShareAPI实例
        UMShareAPI umShareAPI =  UMShareAPI.get(LoginActivity.this);
        umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //获取头像
                String img  = map.get("profile_image_url");
                Uri uri = Uri.parse(img);
                image_haned.setImageURI(uri);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
    private void initData() {
        Intent intent =getIntent();
        int pid = intent.getIntExtra("pid", 0);
        Map<String,String> params =new HashMap<>();
        params.put("pid",String.valueOf(pid));
        iPresenter.getRequeryData(Apis.URl_DATA_DETAILS,params,ChildBean.class);
    }

    private void initView() {
        ButterKnife.bind(this);

        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                String s = (String) model;
                Glide.with(LoginActivity.this).load(s).into((ImageView) view);
            }
        });
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ChildBean) {
            ChildBean childBean = (ChildBean) o;
            if (childBean == null || !childBean.isSuccess()) {
                Toast.makeText(LoginActivity.this, childBean.getMsg(), Toast.LENGTH_SHORT).show();
            } else {
               title.setText(childBean.getData().getTitle());
               price.setText("¥："+childBean.getData().getPrice());
               sub(childBean.getData().getImages());
               xBanner.setData(image,null);
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
    @Override
    protected void onResume() {
        super.onResume();
        xBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xBanner.stopAutoPlay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}