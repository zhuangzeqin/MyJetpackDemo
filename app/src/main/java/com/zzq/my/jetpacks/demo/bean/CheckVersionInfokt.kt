package com.zzq.my.jetpacks.demo.bean
import android.os.Parcelable
//import android.support.annotation.Keep
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
//import kotlinx.parcelize.Parcelize
//import kotlinx.android.parcel.Parcelize
import java.io.Serializable
//更改旧的import语句，将：
//
//import kotlinx.android.parcel.Parcelize
//1
//改为：
//
//import kotlinx.parcelize.Parcelize

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/3/17-16:38
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Keep
@Parcelize
data class CheckVersionInfokt(
    val code: Int?,
    val count: Int?,
    val `data`: Data?,
    val message: String?,
    val success: Boolean?
): Parcelable {
    @Keep
    @Parcelize
    data class Data(
        val app_url: String?,
        val down_flag: Int?,
        val lowest_version: String?,
        val protocolVersion: String?,
        val ver_desc: String?,
        val version: String?
    ): Parcelable
}