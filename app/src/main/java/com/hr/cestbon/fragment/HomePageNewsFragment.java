package com.hr.cestbon.fragment;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hr.cestbon.lenews.R;
import com.hr.cestbon.model.DatePageResult;
import com.hr.cestbon.model.News;
import com.hr.cestbon.utils.Constants;
import com.loopj.android.image.SmartImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
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
    @ViewInject(R.id.magic_indicator2)
    private MagicIndicator magicIndicator;
    @ViewInject(R.id.homeListView)
    private ListView listView;

    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR"};
    //, "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};

    private List<News> newsList = new ArrayList<News>();

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

        initMagicIndicator2();

        getServiceData();
    }

    private void initMagicIndicator2() {
//        MagicIndicator magicIndicator = (MagicIndicator)findViewById(R.id.magic_indicator2);
        CircleNavigator circleNavigator = new CircleNavigator(this.getActivity());
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.WHITE);
        circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void getServiceData(){
        RequestParams params = new RequestParams("http://hiwbs101083.jsp.jspee.com.cn/NewsServlet");
        // 有搜索框的时候实现
//        params.addQueryStringParameter("wd", "xUtils");
        Log.i(Constants.tag, "getServiceData---->");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                // 2: Gson 把string转化为model /map
                Log.i(Constants.tag, "HomePageNewsFragment---->onSuccess-->result: "+result);
                Gson gson = new Gson();
//                final List<News> newsList = gson.fromJson(result, new TypeToken<ArrayList<News>>() {
                //                }.getType());
                DatePageResult fromJson = gson.fromJson(result, DatePageResult.class);
                Log.i("jxy", "从后台返回的数据为:" + fromJson);

                newsList = fromJson.getNewsList();

                // 对数据进行赋值(List_Item的数据适配)
                listView.setAdapter(new HomeListViewAdapter());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(Constants.tag, "getServiceData---->onError");
                Toast.makeText(x.app(), "onError" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(Constants.tag, "getServiceData---->onCancelled");
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override  // 只要请求完毕,无论成功还是失败,此方法都会执行
            public void onFinished() {
                // 下拉刷新完毕
                Log.i(Constants.tag, "getServiceData---->onFinished");
//                listView.endPulldownToRefresh();
            }

        });
    }

    private class HomeListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            News obj = (News) getItem(position);
            Log.i(Constants.tag, "getView---->News:"+obj);
            View view = null;
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(HomePageNewsFragment.this.getActivity(), R.layout.home_page_listitem, null);
            }
            TextView title = (TextView)view.findViewById(R.id.home_title);
            Log.i(Constants.tag, "getView---->TextView:"+title);
            if(title!=null){
                title.setText(obj.getTitle());
            }

            SmartImageView iv = (SmartImageView) view.findViewById(R.id.home_iv);
            //通过XUTLIS3下载图片
            ImageOptions options = new ImageOptions.Builder()
                    // 是否忽略GIF格式的图片
                    .setIgnoreGif(false)
                    // 图片缩放模式
                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                    // 下载中显示的图片
                    .setLoadingDrawableId(R.drawable.time_loading)
                    // 支持缓存操作,默认true
                    .setUseMemCache(true)
                    // 下载失败显示的图片
                    .setFailureDrawableId(R.drawable.time_fail)
                    // 得到ImageOptions对象
                    .build();

            // 通过xutils3 下载图片,并且支持缓存功能
            x.image().bind(iv, obj.getImgUrl(), options);

            return view;
        }
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



}
