package com.cmonbaby.eventbus.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cmonbaby.eventbus.annotation.Subscribe;
import com.cmonbaby.eventbus.annotation.mode.ThreadMode;
import com.cmonbaby.eventbus.core.EventBus;
import com.cmonbaby.eventbus.demo.model.UserInfo;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    // post event
    public void post(View view) {
        EventBus.getDefault().post(new UserInfo("simon", 35));
        finish();

        // post event from thread
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new UserInfo("simon", 35));
                finish();
            }
        }).start();*/
    }

    // register sticky event
    public void sticky(View view) {
        EventBus.getDefault().register(this);
        EventBus.getDefault().removeStickyEvent(UserInfo.class);
    }

    // subscribe sticky method
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sticky(UserInfo user) {
        Log.e("sticky", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // sample coding
        UserInfo userInfo = EventBus.getDefault().getStickyEvent(UserInfo.class);
        if (userInfo != null) {
            UserInfo info = EventBus.getDefault().removeStickyEvent(UserInfo.class);
            if (info != null) {
                EventBus.getDefault().removeAllStickyEvents();
            }
        }
        EventBus.getDefault().unregister(this);
    }
}
