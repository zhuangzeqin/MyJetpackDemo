package com.zzq.my.jetpacks.common.net.base

import androidx.lifecycle.Observer
import com.zzq.my.jetpacks.common.utils.StatusUtils

/**
 * MutableLiveData回调函数，处理请求成功，失败，完成回调
 *
 * @author wangjian
 * Created on 2020/10/29 9:04
 */
interface SimpleObserver<T> : Observer<RefurbishUIMessage<T>> {

    override fun onChanged(msg: RefurbishUIMessage<T>) {
        when (msg.code) {
            StatusUtils.SUCCESS.code -> {
                onSuccess(msg.data)
            }
            StatusUtils.FAIL.code -> {
                onFail(msg.code, msg.msg)
            }
            StatusUtils.FINISH.code -> {
                onFinish()
            }
        }
    }

    fun onSuccess(value: T?)

    fun onFail(code: Int, msg: String?)

    fun onFinish(){}

}


