package com.cmonbaby.eventbus.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cmonbaby.eventbus.annotation.Subscribe;
import com.cmonbaby.eventbus.annotation.mode.ThreadMode;
import com.cmonbaby.eventbus.apt.EventBusIndex;
import com.cmonbaby.eventbus.core.EventBus;
import com.cmonbaby.eventbus.demo.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            UserInfo user = (UserInfo) msg.obj;
            tv.setText(user.toString());
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        // create indexes for apt
        EventBus.getDefault().addIndex(new EventBusIndex());
        // register listener
        EventBus.getDefault().register(this);
    }

    // click
    public void jump(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    // post sticky event
    public void sticky(View view) {
        EventBus.getDefault().postSticky(new UserInfo("simon", 35));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getUser(UserInfo user) {
        // threadMode = ThreadMode.MAIN
        // tv.setText(user.toString());
        // Log.e("abc", user.toString());

        Message msg = new Message();
        msg.obj = user;
        // msg.what = 666;
        handler.sendMessage(msg);
        Log.e("getUser() >>> ", user.toString());
    }

    // The higher number the quantity, the higher the priority
    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 1)
    public void getUserPriority(UserInfo user) {
        // threadMode = ThreadMode.MAIN
        // tv.setText(user.toString());
        Log.e("getUserPriority() >>> ", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        EventBus.clearCaches();
    }
}
