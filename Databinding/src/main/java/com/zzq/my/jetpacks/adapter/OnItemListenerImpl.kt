package com.zzq.my.jetpacks.adapter

import android.view.View

/**
 * 描述：rv 点击事件的实现接口类
 * 作者：zhuangzeqin
 * 时间: 2021/5/13-14:33
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
//这里使用了别名；避免大量的重复函数形参
typealias onItemCallBackParam<T> = (view: View?, item: T, position: Int) -> Unit
class OnItemListenerImpl<T> : IOnItemListener<T> {
    //1 通过高阶函数声明函数类型变量（参数与接口互调函数一致）
    private lateinit var onItemClickCallBack: onItemCallBackParam<T>
    private lateinit var onItemLongCallBack: onItemCallBackParam<T>
    fun itemClickCallBack(callBack: onItemCallBackParam<T>) {
        this.onItemClickCallBack = callBack
    }
    fun itemLongCallBack(callBack: onItemCallBackParam<T>) {
        this.onItemLongCallBack = callBack
    }
    /**
     * 点击事件
     * 业务使用 实现接口内回调方法,调用对应函数变量的invoke(param)方法实现回调
     */
    override fun onItemClickBiz(view: View?, item: T, position: Int) {
        onItemClickCallBack.invoke(view,item,position)
    }

    /**
     * 长按事件
     * 业务使用 实现接口内回调方法,调用对应函数变量的invoke(param)方法实现回调
     */
    override fun onItemLongClickBiz(view: View?, item: T, position: Int) {
        onItemLongCallBack.invoke(view,item,position)
    }
}