package com.zzq.my.jetpacks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

 /*
   * ================================================
   * 描述：ListView 抽象出 databinding Adapter 适配器 基类
   * 作者：zhuangzeqin
   * 时间: 2021/4/22-10:25
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
abstract class BaseLvBindingAdapter<T, vb : ViewDataBinding>(context: Context) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    //数据的集合
    private var mData: MutableList<T> = mutableListOf()

    override fun getCount(): Int = if (mData.isNullOrEmpty()) 0 else mData.size

    override fun getItem(position: Int): T = mData[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var cView: View? = convertView
        val binding: vb
        if (cView == null) {
            binding = DataBindingUtil.inflate(mLayoutInflater, getLayoutId(), parent, false)
            cView = binding.root
            cView.tag = binding
        } else {
            binding = cView.tag as vb
        }
        //设置绑定data 标签的变量 名称
        binding.setVariable(variableId(), getItem(position))
        return binding.root
    }

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
    abstract fun getLayoutId(): Int

    /**
     * data 标签的变量标识
     */
    abstract fun variableId(): Int
}