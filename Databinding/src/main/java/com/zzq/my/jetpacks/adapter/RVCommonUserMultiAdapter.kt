package com.zzq.my.jetpacks.adapter

import android.content.Context
import com.zzq.my.jetpacks.bean.MultiMsg
import com.zzq.my.jetpacks.databinding.BR
import com.zzq.my.jetpacks.databinding.R

/**
 * 描述：RecyclerView  多布局的封装使用
 * 作者：zhuangzeqin
 * 时间: 2021/5/6-9:59
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class RVCommonUserMultiAdapter(context: Context):BaseRvBindingMultiLayoutAdapter<MultiMsg>(context) {
    /**
     * 布局id
     */
    override fun getLayoutId(viewTypeIndex: Int): Int {
        return if (viewTypeIndex==0) R.layout.multil_item_lv1 else R.layout.multil_item_lv2
    }

    /**
     * data 标签的变量标识
     */
    override fun variableId(viewTypeIndex: Int):  Int = if (viewTypeIndex==0) BR.MultiMsgBean else BR.MultiMsgMeta

    /**
     * 根据索引返回使用哪种样式索引
     */
    override fun itemViewType(position: Int): Int {
        return if (position % 2 == 0) 0 else 1
    }
}