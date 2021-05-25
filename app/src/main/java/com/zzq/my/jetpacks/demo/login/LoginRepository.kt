package com.zzq.my.jetpacks.demo.login

import com.zzq.my.jetpacks.common.bean.LoginInfo
import com.zzq.my.jetpacks.common.net.api.ApiService
import com.zzq.my.jetpacks.common.net.base.BaseRepository
import com.zzq.my.jetpacks.common.net.base.BaseResponse

/**
 * 描述：登录repository
 * 作者：zhuangzeqin
 * 时间: 2021/5/25-15:56
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class LoginRepository : BaseRepository() {
    /**
     * 登录
     */
    suspend fun reqLogin(parameters: Map<String, String>): BaseResponse<LoginInfo.DataBean>{
        return ApiService.reqLogin(parameters)
    }
}