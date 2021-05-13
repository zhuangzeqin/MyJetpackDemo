package com.zzq.my.jetpacks.adapter

import android.view.View

/**
 * 描述：Rv 点击，长按事件的接口定义
 * 作者：zhuangzeqin
 * 时间: 2021/5/13-14:04
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
interface IOnItemListener<T> {
    /**
     * 点击事件
     */
    fun onItemClickBiz(view: View?, item: T, position: Int)
    /**
     * 长按事件
     */
    fun onItemLongClickBiz(view: View?, item: T, position: Int)
}