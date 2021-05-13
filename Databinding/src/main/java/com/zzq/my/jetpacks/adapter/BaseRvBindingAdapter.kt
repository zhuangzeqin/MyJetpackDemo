package com.zzq.my.jetpacks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/*
  * ================================================
  * 描述：rv 适配器基类封装
  * 作者：zhuangzeqin
  * 时间: 2021/4/26-19:37
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
abstract class BaseRvBindingAdapter<T, vb : ViewDataBinding>(
    var context: Context,
    block: (OnItemListenerImpl<T>.() -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //实例化回调接口实现类---延迟初始化 而且当且仅当变量被第一次调用的时候，委托方法才会执行
    private val callBack: OnItemListenerImpl<T> by lazy { OnItemListenerImpl<T>() }

    init {
        //将参数内的回调函数与实例化对象绑定
        block?.let { callBack.it() }
    }

    //数据的集合
    private var mData: MutableList<T> = mutableListOf()
    open fun add(t: T?) {
        t?.let { this.mData.add(it) }
        notifyDataSetChanged()
    }

    open fun addAll(list: List<T>?) {
        list?.let { this.mData.addAll(it) }
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        this.mData.removeAt(position)
        notifyDataSetChanged()
    }

    open fun remove(obj: T) {
        if (obj in this.mData) {
            this.mData.remove(obj)
            notifyDataSetChanged()
        }
    }

    open fun setList(list: List<T>?) {
        this.mData.clear()
        list?.let { this.mData.addAll(it) }
        notifyDataSetChanged()
    }

    open fun removeAll() {
        this.mData.clear()
        notifyDataSetChanged()
    }

    /**
     * RecyclerView知道了每一个子项
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: vb = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            getLayoutId(),
            parent,
            false
        )
        return BaseBindingViewHolder(binding.root)

    }

    /**
     * 让每个子项得以显示正确的数据。
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<vb>(holder.itemView)

        /**
         * 绑定item
         */
        val dataItem = mData[position]
        binding?.apply {
            this.setVariable(variableId(), dataItem)
            this.executePendingBindings()
        }
        holder.itemView.run {
            //业务层实现点击事件
            setOnClickListener { callBack.onItemClickBiz(it, dataItem, position)}
            //业务层实现长按事件
            setOnLongClickListener {  callBack.onItemLongClickBiz(it, dataItem, position)
                true}
        }
    }


    override fun getItemCount(): Int = if (mData.isNullOrEmpty()) 0 else mData.size

    /**
     * 布局id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * data 标签的变量标识
     */
    protected abstract fun variableId(): Int

}

/**
 * 把ViewHolder提出来，这样所有Adapter都可以使用而无需在每个Adapter中都声明一个ViewHolder
 */
class BaseBindingViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)