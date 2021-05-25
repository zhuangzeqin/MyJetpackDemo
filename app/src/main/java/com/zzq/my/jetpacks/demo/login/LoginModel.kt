package com.zzq.my.jetpacks.demo.login

import androidx.lifecycle.MutableLiveData
import com.zzq.my.jetpacks.common.bean.LoginInfo
import com.zzq.my.jetpacks.common.net.base.AppModel
import com.zzq.my.jetpacks.common.net.base.RefurbishUIMessage

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/5/25-16:00
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class LoginModel: AppModel<LoginRepository>() {
    val banners: MutableLiveData<RefurbishUIMessage<LoginInfo.DataBean>> = MutableLiveData()
    /**
     * 返回过滤掉BaseResponse之后的data数据
     */
    fun reqLogin(parameters: Map<String, String>): MutableLiveData<RefurbishUIMessage<LoginInfo.DataBean>> {
        sendBaseResponseRequest({mRepository.reqLogin(parameters)}, {
            banners.value = it
        })
        return banners
    }

}