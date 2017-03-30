package com.hr.cestbon.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cestbon on 2017/3/29.
 */

public class SharedPreUtils {

    private static final  String config= "news";

    public static void setBoolean(Context ctx, String key, Boolean value){
        SharedPreferences ref = ctx.getSharedPreferences(config,Context.MODE_PRIVATE);
        ref.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context ctx,String key,Boolean defValue){
        SharedPreferences ref = ctx.getSharedPreferences(config,Context.MODE_PRIVATE);
        return ref.getBoolean(key,defValue);
    }

}
