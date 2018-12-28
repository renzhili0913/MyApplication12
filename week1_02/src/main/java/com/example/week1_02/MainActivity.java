package com.example.week1_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week1_02.adapter.MyDataAdapter;
import com.example.week1_02.bean.ShopBean;
import com.example.week1_02.presenter.IPresenterImpl;
import com.example.week1_02.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {
    @BindView(R.id.image_return)
    ImageButton imageReturn;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_search)
    ImageButton imageSearch;
    @BindView(R.id.image_switch)
    ImageButton imageSwitch;
    @BindView(R.id.text_comprehensive)
    TextView textComprehensive;
    @BindView(R.id.text_volume)
    TextView textVolume;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.text_screen)
    TextView textScreen;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private int mpage;
    private boolean falg =true;
    private int mSrot=0;
    private MyDataAdapter myDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        ButterKnife.bind(this);

        getRecyclerView();
        initData();
    }

    private void initView() {
        mpage=1;
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

       // getRecyclerView();

    }

    private void getRecyclerView() {
        if (falg){
            //image_switch.setBackgroundResource(R.drawable.ic_action_grid);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(linearLayoutManager);
        }else{
            //image_switch.setBackgroundResource(R.drawable.ic_action_list);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(gridLayoutManager);
        }
        //创建适配器
        myDataAdapter = new MyDataAdapter(this, falg);
        xRecyclerView.setAdapter(myDataAdapter);
        myDataAdapter.setOnClickListener(new MyDataAdapter.Click() {
            @Override
            public void onClick(int pid) {
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("pid",pid);
                //EventBus.getDefault().post(pid);
                startActivity(intent);
            }
        });
        falg=!falg;
        initView();
    }

    private void initData() {
        Map<String,String> params = new HashMap<>();
        params.put("keywords",editSearch.getText().toString().trim());
        params.put("page",String.valueOf(mpage));
        params.put("sort",String.valueOf(mSrot));
        iPresenter.getRequeryData(Apis.URl_DATA,params,ShopBean.class);
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ShopBean){
            ShopBean shopBean = (ShopBean) o;
            if (shopBean==null||!shopBean.isSuccess()){
                Toast.makeText(MainActivity.this,shopBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                if (mpage==1){
                    myDataAdapter.setList(shopBean.getData());
                }else{
                    myDataAdapter.addList(shopBean.getData());
                }
                mpage++;
                xRecyclerView.refreshComplete();
                xRecyclerView.loadMoreComplete();
            }
        }
    }

    @OnClick({R.id.image_return, R.id.image_search, R.id.image_switch, R.id.text_comprehensive, R.id.text_volume, R.id.text_price, R.id.text_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.image_search:
                initView();
                initData();
                break;
            case R.id.image_switch:
                //判断状态值转换按钮背景图片
                if (falg){
                    imageSwitch.setBackgroundResource(R.drawable.ic_action_list);
                }else{
                    imageSwitch.setBackgroundResource(R.drawable.ic_action_grid);
                }
                //获取适配器中的数据
                List<ShopBean.DataBean> data = myDataAdapter.getList();
                //调用从新判断加载网格或线性布局
                getRecyclerView();
                //传值到适配器中重新赋值
                myDataAdapter.setList(data);
                break;
            case R.id.text_comprehensive:
                if (!textComprehensive.isSelected()) {
                    mpage = 1;
                    mSrot = 0;
                    initData();
                    textComprehensive.setSelected(true);
                    textVolume.setSelected(false);
                    textPrice.setSelected(false);
                }
                break;
            case R.id.text_volume:
                if (!textVolume.isSelected()) {
                    mpage = 1;
                    mSrot = 1;
                    initData();
                    textComprehensive.setSelected(false);
                    textVolume.setSelected(true);
                    textPrice.setSelected(false);
                }
                break;
            case R.id.text_price:
                if (!textPrice.isSelected()) {
                    mpage = 1;
                    mSrot = 2;
                    initData();
                    textComprehensive.setSelected(false);
                    textVolume.setSelected(false);
                    textPrice.setSelected(true);
                }
                break;
            case R.id.text_screen:
                break;
                default:
                    break;
        }
    }
}
