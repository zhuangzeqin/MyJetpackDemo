package com.zzq.my.jetpacks.common.net.base

import java.io.Serializable

/**
 * 描述：数据类 抽象出后台接口返回的数据格式
 * {
 * "code": 200,
 * "message": "",
 * "data": {
 * "userName": "前海移联直营",
 * "password": null,
 * "agentOem": null,
 * "loginToken": "9fbcde8d-68c0-49a0-9b6d-f2c370cf0bd2",
 * "userId": "1000000000000002897",
 * "agentNo": "1446",
 * "agentNode": "0-1446-",
 * "agentName": "E前海移联直营read"
 * },
 * "count": 0,
 * "success": true
 * }
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-10:10
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
data class BaseResponse<T>(
    val code: Int,//业务标识码，200
    val message: String,//错误提示语
    val data: T,//泛型T 数据
    val count: Int,//数据数量
    val success: Boolean//是否成功； true 代表成功； false 代表不成功
): Serializable