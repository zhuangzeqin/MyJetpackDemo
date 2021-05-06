package com.zzq.my.jetpacks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/4/28-18:02
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
abstract class BaseRvBindingMultiLayoutAdapter<T>(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //数据的集合
    private var mData: MutableList<T> = mutableListOf()
    private val mLayoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        viewType 可以根据来判断不同的viewHolder

        val binding: ViewDataBinding = DataBindingUtil.inflate(
            mLayoutInflater,
            getLayoutId(viewType),
            parent,
            false
        )
        return BaseBindingMultiLayoutViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
        /**
         * 绑定item
         */
        binding?.apply {
            this.setVariable(variableId(getItemViewType(position)), mData[position])
            this.executePendingBindings()
        }
    }

    /**
     * 这里主要是区分定义item的样式，并返回样式的值，这里的返回值为int对象
     */
    override fun getItemViewType(position: Int): Int {
        return itemViewType(position)
    }

    override fun getItemCount(): Int = if (mData.isNullOrEmpty()) 0 else mData.size

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
     * 布局id
     */
    @LayoutRes
    protected abstract fun getLayoutId(viewTypeIndex: Int): Int

    /**
     * data 标签的变量标识
     */
    protected abstract fun variableId(viewTypeIndex: Int): Int


    /**
     * 根据索引返回使用哪种样式索引
     */
    protected abstract fun itemViewType(position: Int): Int
}

/**
 * 把ViewHolder提出来，这样所有Adapter都可以使用而无需在每个Adapter中都声明一个ViewHolder
 */
class BaseBindingMultiLayoutViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView)