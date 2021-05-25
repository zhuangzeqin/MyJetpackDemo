package com.zzq.my.jetpacks.common.net.base
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzq.my.jetpacks.common.utils.CommonUtil
import kotlinx.coroutines.*


/**
 * 描述：BaseViewModel 封装
 * 作者：zhuangzeqin
 * 时间: 2021/5/24-10:53
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
open class BaseViewModel<T>: ViewModel() {
    /**
     * 根据泛型自动初始化Repository实例
     */
    val mRepository: T by lazy {
        (CommonUtil.getClass<T>(this))
            .getDeclaredConstructor()
            .newInstance()
    }
    /**
     * 所有协程网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程，即调用 Job.cancel()方法
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

}