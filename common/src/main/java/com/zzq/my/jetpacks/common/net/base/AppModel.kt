package com.zzq.my.jetpacks.common.net.base

import kotlinx.coroutines.CoroutineScope

/**
 * 描述：封装app层的一些特殊需求
 * 作者：zhuangzeqin
 * 时间: 2021/5/25-15:50
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
open class AppModel<T : BaseRepository> : BaseViewModel<T>() {
    /**
     * 发送请求，并且请求的响应需要是IBaseResponse，并自定义异常处理，
     * 最后响应数据通过Message包装后返回
     *
     * @param block 请求函数
     * @param onResult 请求结果回调，在Message中封装了异常和成功信息
     */
    fun <Data> sendBaseResponseRequest(
        block: suspend CoroutineScope.() -> IBaseResponse<Data>,
        onResult: (RefurbishUIMessage<Data>?) -> Unit
    ) {
        launchUI {
            val result = mRepository.getBaseResponseMessageWithError({ block() }, {
                // 特殊异常处理，如：需登录，可在此处统一处理登录跳转
                RefurbishUIMessage.getFailMessage(it.errMsg)
            })
            onResult(result)
        }
    }


    /**
     * 发送请求的响应实体可以是任意类型
     *
     * @param block 请求函数
     * @param onResult 请求结果回调，在Message中封装了异常和成功信息
     */
    fun <Data> sendRequest(
        block: suspend CoroutineScope.() -> Data,
        onResult: (RefurbishUIMessage<Data>?) -> Unit
    ) {
        launchUI {
            val result = mRepository.getResponseWithError({ block() }, {
                // 特殊异常处理，如：需登录，可在此处统一处理登录跳转
             RefurbishUIMessage.getFailMessage(it.errMsg)
            })
            onResult(result)

        }
    }
}