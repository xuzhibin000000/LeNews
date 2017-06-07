package com.hr.cestbon.customerview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cestbon on 2017/4/7.
 */

public class MyViewPage extends ViewPager {
    public MyViewPage(Context context) {
        super(context);
    }

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override  //次方法用于事件分发机制
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //阻止父容器
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        return super.onTouchEvent(ev);
//        return false; //禁用ViewPager左右滑动
//    }
}
