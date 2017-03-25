package com.hr.cestbon.lenews;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private ViewPager vp = null;
    private LinearLayout ll = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化试图
        initView();
        //初始化数据
        intiData();
    }

    /*
    初始化数据
     */
    private void intiData() {

        if (vp != null) {
            vp.setAdapter(new GuideAdapter());
        }


    }

    /*
      初始化引导页Activity的VIEW ITEM
     */
    private void initView() {
        vp = (ViewPager) findViewById(R.id.viewpager);
        ll = (LinearLayout) findViewById(R.id.ll);
        View point = null;
        //向承载3个灰圆圈的布局文件中动态添加圆圈
        Log.i("xzb","initView");
        for (int i = 0; i < 3; i++) {
            point = new View(GuideActivity.this);
            point.setBackgroundResource(R.drawable.guide_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            if (i > 0) {
                params.leftMargin = 20;
            }
            point.setLayoutParams(params);
            Log.i("xzb", "Point：" + point);
            ll.addView(point);
        }

    }

    private class GuideAdapter extends PagerAdapter {
        private int[] imgs = null;
        private List<ImageView> list = null;

        public GuideAdapter() {
            super();
            imgs = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
            list = new ArrayList<ImageView>();
            ImageView iv = null;
            for (int i = 0; i < imgs.length; i++) {
                iv = new ImageView(GuideActivity.this);
                iv.setBackgroundResource(imgs[i]);
                list.add(iv);
            }


        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = (ImageView) list.get(position);
            Log.i("xzb", "当前的ViewPager对象：" + container + ",position:" + position + ",ImageVIew:" + iv);
            container.addView(iv);
            return iv;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
