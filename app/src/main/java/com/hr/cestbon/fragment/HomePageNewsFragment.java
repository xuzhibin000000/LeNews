package com.hr.cestbon.fragment;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.cestbon.lenews.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cestbon on 2017/4/8.
 * Fragment需要添加xml布局文件
 */
@ContentView(R.layout.home_page_news)
public class HomePageNewsFragment extends Fragment {

    // 视图对象
    @ViewInject(R.id.viewPagerNews)
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //给ViewPager添加页面适配器
        viewPager.setAdapter(new PageAdapter());
    }


    private class PageAdapter extends PagerAdapter {
        private List<View> vList = new ArrayList<View>();
        private int[] imgList = {R.drawable.news_viewpager01, R.drawable.news_viewpager02, R.drawable.news_viewpager03};

        public PageAdapter() {
            for (int i = 0; i < imgList.length; i++) {
                ImageView iv = new ImageView(HomePageNewsFragment.this.getActivity());
                iv.setBackgroundResource(imgList[i]);
                vList.add(iv);
            }
        }

        @Override
        public int getCount() {
            return vList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = vList.get(position);
            container.addView(view);
            return view;
//            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Log.i(Constants.tag,"onViewCreated----->");
//
//        viewPager = (ViewPager) this.getActivity().findViewById(R.id.vp_view);
//
//        tabLayout = (TabLayout) this.getActivity().findViewById(R.id.tabLayout);
//        // 新建适配器
//        tabAdaper = new TabAdaper(this.getActivity().getSupportFragmentManager());
//        // 设置适配器
//        viewPager.setAdapter(tabAdaper);
//        // 直接绑定viewpager，消除了以前的需要设置监听器的繁杂工作
//        tabLayout.setupWithViewPager(viewPager);
//
//    }
//
//    // fragment的适配器类
//    class TabAdaper extends FragmentPagerAdapter {
//
//        List<Fragment> fragmentList = new ArrayList<>();
//        // 标题数组
//        String[] titles = {"要闻", "直播", "国际", "体育",  "军事"};  //, "财经","军事", "财经", "社会"
//
//        public TabAdaper(FragmentManager fm) {
//            super(fm);
//            fragmentList.add(new KeyNewsFragment());
//            fragmentList.add(new LiveRadioFragment());
//            fragmentList.add(new SportsFragment());
//            fragmentList.add(new InterFragment());
//            fragmentList.add(new FinanceFragment());
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//    }

}
