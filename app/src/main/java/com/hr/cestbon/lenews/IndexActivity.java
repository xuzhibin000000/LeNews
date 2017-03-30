package com.hr.cestbon.lenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hr.cestbon.utils.SharedPreUtils;

import java.util.Timer;
import java.util.TimerTask;

public class IndexActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //2秒后跳转到引导页面
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                 boolean guide_shoe = SharedPreUtils.getBoolean(IndexActivity.this,"guide_show",false);
                if (guide_shoe){
                    Intent intent = new Intent(IndexActivity.this,MainActivity.class);
                    //标准模式中，所有的Activity在一个APP中都在同一个栈，打开新的Activity时，销毁当前的Activity栈，然后创建一个新的栈，这样新打开的Activity在栈底
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(IndexActivity.this,GuideActivity.class));
                }


            }
        },2000);
    }
}
