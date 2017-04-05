package com.hr.cestbon.utils;

import android.content.Context;

/**
 * Created by cestbon on 2017/3/30.
 */

public class DensityUtils {

    public static int dpi2px(Context ctx, int dpi){
        return (int)(ctx.getResources().getDisplayMetrics().density * dpi );
    }

    public static int px2dpi(Context ctx,int px){
        return (int)(px / ctx.getResources().getDisplayMetrics().density);
    }

}
