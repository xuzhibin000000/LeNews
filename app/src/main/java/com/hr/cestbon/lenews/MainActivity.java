package com.hr.cestbon.lenews;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hr.cestbon.customerview.BottomMenu;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BottomMenu imageViewGroup = (BottomMenu) findViewById(R.id.bMenu);
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(imageViewGroup.getWidth(),imageViewGroup.getHeight());
//        String[] string={"B1","B2","B3","B4","B5"};
//        for (int i = 0; i < 5; i++) {
//            Button textView = new Button(this);
//            textView.setText(string[i]);
//            textView.setTextColor(Color.WHITE);
//            textView.setBackgroundResource(R.drawable.guide_point_gray);
//            imageViewGroup.addView(textView);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        System.exit(0);
        removeALLActivity();
    }
}
