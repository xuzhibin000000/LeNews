package com.hr.cestbon.customerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hr.cestbon.lenews.R;
import com.hr.cestbon.utils.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cestbon on 2017/3/31.
 * 将底部的菜单用自定义控件的方式实现
 */

public class BottomMenu extends RadioGroup {

    private int childVerticalSpace;
    private int childHorizontalSpace;

    private ArrayList<Integer> dataSource;

    public BottomMenu(Context context) {
        super(context);
    }

    public BottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }



    private void initView(Context context,AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomMenu);
        if (array != null) {
            childHorizontalSpace = array.getDimensionPixelSize(R.styleable.BottomMenu_tagHorizontalSpace, 0);
            childVerticalSpace = array.getDimensionPixelSize(R.styleable.BottomMenu_tagVerticalSpace, 0);
            array.recycle();
        }
        String[] string={"B1","B2","B3","B4","B5","B2","B3","B4","B5"};
        for (int i = 0; i < string.length; i++) {
            RadioButton radioButton = new RadioButton(this.getContext());
            radioButton.setText(string[i]);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
//            radioButton.setBackgroundResource(R.drawable.guide_point_gray);
//            textView.setGravity(1);
//            textView.setWidth(0);
            LayoutParams params = new LayoutParams(context,attrs);

//            radioButton.setLayoutParams(params);
            this.addView(radioButton);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(Constants.tag, "BottomMenu=====>onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        int count = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 遍历每个子元素
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + childHorizontalSpace;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + childVerticalSpace;

            if (lp != null && lp instanceof MarginLayoutParams) {
                MarginLayoutParams params = (MarginLayoutParams) lp;
                childWidth += params.leftMargin + params.rightMargin;
                childHeight += params.topMargin + params.bottomMargin;
            }

            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
                child.setTag(new Location(left, top + height, childWidth + left - childHorizontalSpace, height + child.getMeasuredHeight() + top));
            } else {// 否则累加值lineWidth,lineHeight取最大高度
                child.setTag(new Location(lineWidth + left, top + height, lineWidth + childWidth - childHorizontalSpace + left, height + child.getMeasuredHeight() + top));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }
        width = Math.max(width, lineWidth) + getPaddingLeft() + getPaddingRight();
        height += lineHeight;
        sizeHeight += getPaddingTop() + getPaddingBottom();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(Constants.tag, "BottomMenu=====>onLayout");
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(Constants.tag, "BottomMenu=====>onDraw");
    }




    public class Location {
        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int left;
        public int top;
        public int right;
        public int bottom;

    }
}
