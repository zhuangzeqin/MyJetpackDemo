package com.zzq.my.jetpacks.adapter

import android.content.Context
import com.zzq.my.jetpacks.bean.CommonUser
import com.zzq.my.jetpacks.databinding.BR
import com.zzq.my.jetpacks.databinding.R
import com.zzq.my.jetpacks.databinding.databinding.ItemLvBinding

/*
  * ================================================
  * 描述：普通listView 的适配器
  * 作者：zhuangzeqin
  * 时间: 2021/4/22-10:28
  * 邮箱：zzq@eeepay.cn
  * 备注:
  * ----------------------------------------------------------------
  * You never know what you can do until you try !
  *      _              _           _     _   ____  _             _ _
  *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
  *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
  *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
  *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
  *
  * 签名：最痛苦的事不是我失败了，而是我本可以.--zzq
  * ----------------------------------------------------------------
  * ================================================
  */
class RVCommonUserAdapter(context: Context) : BaseRvBindingAdapter<CommonUser, ItemLvBinding>(
    context
) {
    /**
     * 布局id
     */
    override fun getLayoutId(): Int = R.layout.item_lv

    /**
     * data 标签的变量标识
     */
    override fun variableId(): Int = BR.user


}