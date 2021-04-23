package com.zzq.my.jetpacks.tools

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/4/21-11:23
 * 邮箱：zzq@eeepay.cn
 * 备注:
 * * todo Kotlin的一大特点，文件内可以多个类，函数。java则不能多个public的类，也不能函数。
 * 用于xml中dataBinding的静态函数.不同于java的public static函数写法。这里kotlin中有两种写法：
 * 1、直接定义在kt文件的top level顶级，直接写函数名。调用方导入BindUtilKt，使用BindUtilKt.ageName应用。
 * 2、定义在一个静态类object中。在kotlin中就是object的静态类，或者companion object的类中。需要@jvmStatic标记才有效
 */

/**
 * 用于xml的databinding中，这是在kt文件顶级写法，不需要static标记
 */
fun ageName(age: Int, name: String): String {
    return "Kt函数：$age$name"
}

/**
 * [context]参数弹toast
 */
fun toastV(context: Context) {
    Toast.makeText(context, "context弹出toast", Toast.LENGTH_SHORT).show()
}

/**
 * 这是在class类中companion的object 中，
 */
class BindUtil {

    companion object {
        @JvmStatic
        fun ageAge(age: Int): String {
            return "年龄$age"
        }
    }
}

/**
 * 写在object中
 */
object BindHelp {
    @JvmStatic
    fun nameName(name: String): String {
        return "姓名$name"
    }

    /**
     * 用于view的静态点击
     */
    @JvmStatic
    fun staticClick(view: View) {
        Toast.makeText(view.context, "静态函数引用", Toast.LENGTH_SHORT).show()
    }
}