package com.hr.cestbon.lenews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hr.cestbon.utils.DensityUtils;
import com.hr.cestbon.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private ViewPager vp = null;
    private LinearLayout ll = null;
    private int pointMoveWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化试图
        initView();
        //初始化数据
        intiData();
        Log.i("LeNews",this.getResources().getDisplayMetrics().density+"");
    }

    /*
    初始化数据
     */
    private void intiData() {

        if (vp != null) {
            vp.setAdapter(new GuideAdapter());
        }
        // 通过对Tree观察者监听,可以动态计算移动的距离
        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("LeNews", "onGlobalLayout........");
                Log.i("LeNews", "0point:" + ll.getChildAt(0).getLeft() + ",1point:" + ll.getChildAt(1).getLeft());
                pointMoveWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                ll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("LeNews", "onGlobalLayout........");
                Log.i("LeNews", "0point:" + ll.getChildAt(0).getLeft() + ",1point:" + ll.getChildAt(1).getLeft());
                pointMoveWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                ll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // 注册一个监听页面切换事件
         vp.addOnPageChangeListener(new GuidePageListener());
    }

    /*
      初始化引导页Activity的VIEW ITEM
     */
    private void initView() {
        int dpi;
        vp = (ViewPager) findViewById(R.id.viewpager);
        ll = (LinearLayout) findViewById(R.id.ll);
        View point = null;
        //向承载3个灰圆圈的布局文件中动态添加圆圈
        Log.i("LeNews", "initView");

        for (int i = 0; i < 4; i++) {
            point = new View(GuideActivity.this);
            point.setBackgroundResource(R.drawable.guide_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dpi2px(this,10), DensityUtils.dpi2px(this,10));
            if (i > 0) {
                params.leftMargin = DensityUtils.dpi2px(this,5);
            }
            point.setLayoutParams(params);
            Log.i("LeNews", "Point：" + point);
            ll.addView(point);
        }

    }

    public void startMainActivity(View view) {

        //如果已经打开过引导页面，则设置标记
        SharedPreUtils.setBoolean(this, "guide_show", true);
        Intent intent = new Intent(this, MainActivity.class);
        //标准模式中，所有的Activity在一个APP中都在同一个栈，打开新的Activity时，销毁当前的Activity栈，然后创建一个新的栈，这样新打开的Activity在栈底
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private class GuideAdapter extends PagerAdapter {
        private int[] imgs = null;
        private List<View> list = null;

        public GuideAdapter() {
            super();
            imgs = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
            list = new ArrayList<View>();
            ImageView iv = null;
            for (int i = 0; i < imgs.length; i++) {
                iv = new ImageView(GuideActivity.this);
                iv.setBackgroundResource(imgs[i]);
                list.add(iv);
            }
            list.add(View.inflate(GuideActivity.this, R.layout.activity_start, null));

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View iv = (View) list.get(position);
            Log.i("LeNews", "当前的ViewPager对象：" + container + ",position:" + position + ",ImageVIew:" + iv);
            container.addView(iv);
            return iv;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class GuidePageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i("LeNews", "Page的索引" + position + ",移动百分比:" + positionOffset + "移动的像素:" +
                    positionOffsetPixels);
            View red_point = findViewById(R.id.red_point);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)red_point.getLayoutParams();
            params.leftMargin = (int)(pointMoveWidth * (position + positionOffset));
            red_point.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            Log.i("LeNews", "当前位置：" + position);
        }

        /**
         * @param state 0 代表未滑动, 1 代表正在滑动 2 代表滑动切图成功
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i("LeNews", "页面滑动的状态：" + state);
        }
    }
}
