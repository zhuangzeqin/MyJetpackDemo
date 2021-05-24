package com.zzq.my.jetpacks.common.net.api

import com.zzq.my.jetpacks.common.net.RetrofitClient

/**
 * 描述：api 请求接口类
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-15:23
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
object ApiService {
    //延迟初始化
    private val mInterfaces by lazy { RetrofitClient.getInstance().create(ApiInterfaces::class.java) }
    //登录api 请求接口
    suspend fun reqLogin(parameters: Map<String, Any>) = mInterfaces.reqLonin(parameters)
}