package com.example.week1_01.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.week1_01.LoginActivity;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle =intent.getExtras();
        if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){
            Intent intent1 =new Intent(context,LoginActivity.class);
            intent1.putExtra("pid",bundle.getString(JPushInterface.EXTRA_ALERT));
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }
    }
}
