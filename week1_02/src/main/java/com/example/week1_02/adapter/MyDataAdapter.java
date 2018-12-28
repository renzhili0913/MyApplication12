package com.example.week1_02.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week1_02.R;
import com.example.week1_02.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class MyDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean> list;
    private boolean falg;
    private View view;

    public MyDataAdapter(Context context, boolean falg) {
        this.context = context;
        this.falg = falg;
        list = new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addList(List<ShopBean.DataBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public List<ShopBean.DataBean> getList() {
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (falg) {
            view = LayoutInflater.from(context).inflate(R.layout.linear_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup, false);

        }
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DataViewHolder holder= (DataViewHolder) viewHolder;
        holder.title.setText(list.get(i).getTitle());
        holder.price.setText("¥："+list.get(i).getPrice());
        holder.salenum.setText("销量："+list.get(i).getSalenum());
        String replace = list.get(i).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        holder.images.setImageURI(uri);
        holder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                 click.onClick(list.get(i).getPid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.images)
        SimpleDraweeView images;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.salenum)
        TextView salenum;
        @BindView(R.id.constraintlayout)
        ConstraintLayout constraintlayout;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    public interface Click{
        void onClick(int pid);
    }
}
