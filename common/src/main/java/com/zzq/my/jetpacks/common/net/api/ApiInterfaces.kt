package com.zzq.my.jetpacks.common.net.api

import com.zzq.my.jetpacks.common.bean.LoginInfo
import com.zzq.my.jetpacks.common.net.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 描述：api 接口定义
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-15:16
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
interface ApiInterfaces {
    //登录接口
    @POST("agentApi2/login/")
    suspend fun reqLonin(@Body parameters: Map<String, String>): BaseResponse<LoginInfo.DataBean>
}