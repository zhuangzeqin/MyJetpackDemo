package com.zzq.my.jetpacks.common.net.interceptors

import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 描述：重试的拦截器
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-9:37
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class RetryInterceptor(private val maxRetry:Int=0):Interceptor {
    val tag = RetryInterceptor::class.java.simpleName
    private var retriedNum: Int = 0//已经重试的次数,注意，设置maxRetry重试次数，作用于重试
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response = chain.proceed(request)
        //如果请求不成功； 并且次数小于总的大小；就继续请求
        while (!response.isSuccessful && retriedNum < maxRetry) {
            retriedNum++
            Logger.t(tag).d("当前retriedNum=$retriedNum")
            response = chain.proceed(request)
        }
        return response
    }
}