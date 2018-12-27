package com.example.week1_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.example.week1_01.adapter.MYDataAdapter;
import com.example.week1_01.bean.ShopBean;
import com.example.week1_01.presenter.IPresenterImpl;
import com.example.week1_01.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private int mpage;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xRecyclerView;
    private MYDataAdapter myDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        initView();

    }

    private void initData() {
        Map<String,String> params =new HashMap<>();
        params.put("keywords","笔记本");
        params.put("page",String.valueOf(mpage));
        iPresenter.getRequeryData(Apis.URl_DATA,params,ShopBean.class);
    }

    private void initView() {
        mpage=1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(gridLayoutManager);
        //创建适配器
        myDataAdapter = new MYDataAdapter(this);
        xRecyclerView.setAdapter(myDataAdapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpage=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
       myDataAdapter.setOnClickListener(new MYDataAdapter.Click() {
           @Override
           public void onClick(int position, int pid) {
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               intent.putExtra("pid",pid);
               startActivity(intent);
           }
       });
    }
    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ShopBean){
            ShopBean shopBean  = (ShopBean) o;
            if (shopBean==null||!shopBean.isSuccess()){
                Toast.makeText(MainActivity.this,shopBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                if (mpage==1){
                    myDataAdapter.setList(shopBean.getData());
                }else{
                    myDataAdapter.addList(shopBean.getData());
                }
                mpage++;
                xRecyclerView.loadMoreComplete();
                xRecyclerView.refreshComplete();
            }
        }
    }
}
