package com.zzq.my.jetpacks.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.zzq.my.jetpacks.databinding.BuildConfig
import com.zzq.my.jetpacks.databinding.R

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
        var str = "静态函数引用"
        val length = str?.length ?: 0// 如果 ?: 左边的表达式不为null，则返回左边表达式的值，否则返回?: 右边表达式的值。
        getValueSafely {
            val value = 1 / 2
            debugMessage("staticClick") {
                value.asType<String>()

            }
        }
    }
}

/**
 * 安全的获取值的信息，其过程中发生异常会自动处理，返回null
 * block 取值操作，可能发生异常 返回null
 */
inline fun <T> getValueSafely(block: () -> T?): T? {
    return try {
        block()
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

/**
 * Kotlin日志输出方法 封装
 * 使用inline修饰高阶函数（参数为函数时）
 * 由于采用了 inline 处理 会把 debugMessage 提取到调用处
 */
inline fun debugMessage(tag: String = "debugMessage", lazyMessage: () -> Any?) {
    if (BuildConfig.DEBUG) {//DEBUG时才执行，否则不执行
        Log.d(tag, lazyMessage().toString())
    }
}

/**
 * 类型转换
 * 使用Kotlin Reified 让泛型更简单安全
 * 使用reified很简单，主要分为两步
在泛型类型前面增加 reified
在方法前面增加 inline（必需的）
 */
inline fun <reified T> Any.asType(): T? {
    return if (this is T) this else null
}

/**
 * Bundle 扩展函数
 */
inline fun <reified T> Bundle.plus(key: String, value: T) {
    when (value) {
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Double -> putDouble(key, value)
        is Char -> putChar(key, value)
    }
}

// Activity related 提取 Bundle 参数
//val firstName by getValue<String>("firstName") // String?
//val lastName by getValueNonNull<String>("lastName") // String
inline fun <reified T : Any> Activity.getValue(
    lable: String, defaultvalue: T? = null
) = lazy {
    val value = intent?.extras?.get(lable)
    if (value is T) value else defaultvalue
}

inline fun <reified T : Any> Activity.getValueNonNull(
    lable: String, defaultvalue: T? = null
) = lazy {
    val value = intent?.extras?.get(lable)
    requireNotNull((if (value is T) value else defaultvalue)) { lable }
}

// Fragment related 提取 Bundle 参数
inline fun <reified T : Any> Fragment.getValue(lable: String, defaultvalue: T? = null) = lazy {
    val value = arguments?.get(lable)
    if (value is T) value else defaultvalue
}

inline fun <reified T : Any> Fragment.getValueNonNull(lable: String, defaultvalue: T? = null) =
    lazy {
        val value = arguments?.get(lable)
        requireNotNull(if (value is T) value else defaultvalue) { lable }
    }

//Extensions
//fun Int.asColor() = ContextCompat.getColor(ApplicationCalss.instance, this)
//fun Int.asDrawable() = ContextCompat.getDrawable(MavrikApplication.instance, this)

//Usage at call site
//val color = R.color.dark_blie.asColor()
//val drawable = R.drawable.launcher.asDrawable()


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

//Extension function
fun String?.valid() : Boolean =
    this != null && !this.equals("null", true)
            && this.trim().isNotEmpty()

inline fun Any.Toast(context: Context,block: () -> Any?){
    Toast.makeText(context, block.invoke().toString() ,Toast.LENGTH_SHORT).show()
}

// Function
inline fun <reified T : Activity> Activity.startActivity(context: Context) {
    startActivity(Intent(context, T::class.java))
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson(json, T::class.java)

/**
 * 泛型实例化 带参的构造函数
 */
inline fun <reified T: Any> new(vararg params: Any): T {
    val clz = T::class.java
    val paramTypes = params.map { it::class.java }.toTypedArray()
    val mCreate = clz.getDeclaredConstructor(*paramTypes)
    mCreate. isAccessible = true
    return mCreate. newInstance(* params)
}
/**
 * 泛型实例化 无参的构造函数
 */
inline fun <reified T: Any> new(): T {
    val clz = T::class.java
    val mCreate = clz.getDeclaredConstructor()
    mCreate. isAccessible = true
    return mCreate. newInstance()
}

inline fun <reified T: Any> new2(vararg params: Any) =
    T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray()).apply { isAccessible = true }.newInstance(*params)

//fun button(text: String, block: Button.() -> Unit) = new<Button>(text).also(block)
//
//fun main(args: Array<String>) {
//    button("Click") {
////        addActionListener { ... }
//    }
//}



// Usage at call site
//if(data.valid())


