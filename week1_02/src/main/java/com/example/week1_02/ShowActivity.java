package com.example.week1_02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.week1_02.adapter.MyFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends AppCompatActivity {

    @BindView(R.id.image_return)
    ImageButton imageReturn;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.image_share)
    ImageButton imageShare;
    @BindView(R.id.image_dian)
    ImageButton imageDian;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.but_text)
    TextView butText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //创建适配器
        viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
    }

    @OnClick({R.id.image_return, R.id.but_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.but_text:
                break;
        }
    }
    public void replace(int position){
        viewpager.setCurrentItem(position);
    }
}
