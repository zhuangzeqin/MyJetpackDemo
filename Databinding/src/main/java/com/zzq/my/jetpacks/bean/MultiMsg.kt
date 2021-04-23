package com.zzq.my.jetpacks.bean
import androidx.annotation.Keep


/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/4/23-17:35
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Keep
data class MultiMsg(
    val message: Message?,
    val meta: Meta?
) {
    @Keep
    data class Message(
        val user_id: Int,
        val user_name: String?,
        val user_sex: String?,
        val user_tel: String?,
    )
    @Keep
    data class Meta(
        val msg: String?,
        val status: Int
    )
}