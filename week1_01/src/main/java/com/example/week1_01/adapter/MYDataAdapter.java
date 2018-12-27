package com.example.week1_01.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.week1_01.LoginActivity;
import com.example.week1_01.R;
import com.example.week1_01.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MYDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean> list;
    private int position;
    public MYDataAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> data) {
        list.clear();
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addList(List<ShopBean.DataBean> data) {
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        position=i;
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(list.get(i).getTitle());
        myViewHolder.price.setText("¥："+list.get(i).getPrice());
        String replace = list.get(i).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        myViewHolder.simpleDraweeView.setImageURI(uri);
        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(i,list.get(i).getPid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
       @BindView(R.id.constraintlayout)
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    public  interface Click{
        void onClick(int position,int pid);
    }
}
