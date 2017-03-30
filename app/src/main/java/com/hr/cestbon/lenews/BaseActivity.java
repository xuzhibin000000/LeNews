package com.hr.cestbon.lenews;


import android.app.Activity;

/**
 * Created by cestbon on 2017/3/29.
 */

public class BaseActivity extends Activity {

    private MyApplication application;
    private BaseActivity baseActivity;

    @Override
    protected void onResume() {
        super.onResume();
        if (application == null) {
            // 得到Application对象
            application = (MyApplication) getApplication();
        }

        baseActivity = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }


    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(baseActivity);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(baseActivity);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }


}
