package com.zzq.my.jetpacks.common.net.base

import com.zzq.my.jetpacks.common.net.exception.ExceptionHandle
import com.zzq.my.jetpacks.common.net.exception.ResponseThrowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * 描述：用于封装协程请求和回调
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-15:59
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class BaseRepository {

    /**
     * 获取请求结果，没有经过处理的最原始的数据
     *
     * @param block 请求协程
     * @param success 请求成功函数回调，默认实现返回Message.getSuccessMessage(it)
     * @param error 异常函数回调，默认实现返回Message.getFailMessage(it.errMsg)
     * @param complete 请求完成函数回调，默认实现返回Message.getFinishMessage()
     */
    suspend fun <T> getResponseMessage(
        block: suspend CoroutineScope.() -> T,
        success: (T) -> BaseResponse<T>? ,
        error: (ResponseThrowable) -> BaseResponse<T>?,
        complete: () -> BaseResponse<T>?
    ): BaseResponse<T>? {
        var message: BaseResponse<T>? = null
        getResponse(block, {
            message = success(it)
        }, {
            message = error(it)
        }, {
            complete() // 此处不可在对message赋值，否则会覆盖success或error中的回调值，因为其实在try中的finally里面执行的
        })

        return message
    }
    /**
     * 获取请求结果，没有经过处理的最原始的数据
     *
     * @param block 请求协程
     * @param success 请求成功函数回调
     * @param error 异常函数回调，默认空实现
     * @param complete 请求完成函数回调，默认空实现
     */
    suspend fun <T> getResponse(
        block: suspend CoroutineScope.() -> T,
        success: suspend CoroutineScope.(T) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {},
        complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        handleException(
            {
                withContext(Dispatchers.IO) {
                    block().let {
                        it ?: throw ResponseThrowable(-1, "getResponse() response is null")
                    }
                }.also { success(it) }
            },
            { error(it) },
            { complete() }
        )
    }
    /**
     * 异常统一处理
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }
}