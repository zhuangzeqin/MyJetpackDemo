package com.zzq.my.jetpacks.tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.zzq.my.jetpacks.databinding.R

/**
 * 描述： Binding高级用法中，辅助工具类，演示@BindingConversion，@bindadapter等
 * 作者：zhuangzeqin
 * 时间: 2021/4/21-10:14
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
object BCTool {
    //todo **
    //	 * 兼容适配view的background的str转color属性，这里函数名 可以随意，而且不需要其他地方显式的调用。
    //	 * 只需要在此 ，静态函数的声明即可。（java的写法就是public static，这里不写java版的了。）
    //	 */
    /**
     * 转换操作
     */
    @BindingConversion
    @JvmStatic
    fun converStr2Color(str: String): Drawable {
        return when (str) {
            "red" -> ColorDrawable(Color.RED)//红色
            "green" -> ColorDrawable(Color.GREEN)//绿色
            "blue" -> ColorDrawable(Color.BLUE)//蓝色
            else -> ColorDrawable(Color.YELLOW)//黄色
        }
    }
    /**
     * 用于appCompatImageView的自定义属性，bind:imgSrc，命名空间bind:可以省略，也就是写作 imgSrc亦可。可以用于加载url的图片
     * 函数名也是随意，主要是value的声明，就是新加的属性名了，可以多个属性同用，并配置是否必须一起作用
     * 函数名随意，方法签名才重要，匹配对象控件，以及属性参数。这里还可以添加old 参数，获取修改新参数 之前对应的值。
     * todo 加载网络图片，需要网络权限，别忘了
     */
    @JvmStatic
    @BindingAdapter(value = ["bind:imgSrc"] ,requireAll = false)
    fun urlImageSrc(view: AppCompatImageView, url: String) {
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.img_banner)
            .centerInside()
            .into(view)
    }

    /**
     * 这个是 databinding高级用法中，配合演示swipeRefreshLayout的刷新状态的感知
     * 第一步：单向的，数据变化，刷新UI
     */
    @JvmStatic
    @BindingAdapter("sfl_refreshing", requireAll = false)
    fun setSwipeRefreshing(view: SwipeRefreshLayout, oldValue: Boolean, newValue: Boolean) {
        //判断是否是新的值，避免陷入死循环
        if (oldValue != newValue)
            view.isRefreshing = newValue
    }

    /**
     * 第二步：ui的状态，反向绑定给数据变化
     */
    @JvmStatic
    @BindingAdapter("sfl_refreshingAttrChanged", requireAll = false)
    fun setRefreshCallback(view: SwipeRefreshLayout, listener: InverseBindingListener?) {

        listener ?: return
        view.setOnRefreshListener {
            //由ui层的刷新状态变化，反向通知数据层的变化
            listener.onChange()
        }
    }
    /**
     * 反向绑定的实现，将UI的变化，回调给bindingListener，listener就会onChange，通知数据变化
     * 注意这里的attr和event，是跟上面两步配合一致才有效
     */
    @JvmStatic
    @InverseBindingAdapter(attribute = "sfl_refreshing", event = "sfl_refreshingAttrChanged")
    fun isSwipeRefreshing(view: SwipeRefreshLayout): Boolean {
        return view.isRefreshing
    }


}