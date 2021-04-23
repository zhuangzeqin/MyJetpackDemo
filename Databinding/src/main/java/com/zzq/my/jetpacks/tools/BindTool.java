package com.zzq.my.jetpacks.tools;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 描述：静态工具类
 * 作者：zhuangzeqin
 * 时间: 2021/4/21-11:21
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class BindTool {

    /**
     * 简单的一个静态函数，databinding在xml中使用的函数，必须是public的static函数
     *
     * @param name 名称
     * @param age  年龄
     * @return string的拼接
     */
    public static String nameAge(String name, int age) {
        return name + age;
    }

    /**
     * 点击就打印一个log
     */
    public static void log() {
        Log.i("BindTool", "dataBinding的普通点击，静态java函数的log日志");
    }

    /**
     * 使用view参数，显示toast
     *
     * @param view view控件
     */
    public static void toast(View view) {
        Toast.makeText(view.getContext(), "普通点击view显示toast", Toast.LENGTH_SHORT).show();
    }
}
