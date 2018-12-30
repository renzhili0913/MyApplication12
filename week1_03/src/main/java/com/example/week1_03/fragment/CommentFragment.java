package com.example.week1_03.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.week1_03.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommentFragment extends Fragment {
    @BindView(R.id.price)
    TextView price;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_fragment, container, false);
        bind = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessage(Object o){
        if (o instanceof Double){
            Double v = (Double) o;
            price.setText(v+"");
        }
    }


   @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind!=null) {
            bind.unbind();
        }
        EventBus.getDefault().unregister(this);
    }
}
