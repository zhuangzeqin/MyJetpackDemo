package com.zzq.my.jetpacks.common.utils

import com.blankj.utilcode.util.PathUtils
import com.tencent.mmkv.MMKV
 /*
   * ================================================
   * 描述：mmkv 高效的sp 工具类
   * 作者：zhuangzeqin
   * 时间: 2021/5/21-14:44
   * 邮箱：zzq@eeepay.cn
   * 备注:
   * ----------------------------------------------------------------
   * You never know what you can do until you try !
   *      _              _           _     _   ____  _             _ _
   *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
   *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
   *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
   *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
   *
   * 签名：最痛苦的事不是我失败了，而是我本可以.--zzq
   * ----------------------------------------------------------------
   * ================================================
   */
object SpUtils {

    private val kv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    init {
        MMKV.initialize(PathUtils.getInternalAppFilesPath())
    }

    fun put(key: String, value: Any?) {
        when (value) {
            is Boolean -> kv.putBoolean(key, value)
            is ByteArray -> kv.putBytes(key, value)
            is Float -> kv.putFloat(key, value)
            is Int -> kv.putInt(key, value)
            is Long -> kv.putLong(key, value)
            is String -> kv.putString(key, value)
            else -> error("${value?.javaClass?.simpleName} Not Supported By CniaoSpUtils")
        }
    }

    fun getBoolean(key: String, defValue: Boolean = false) = kv.getBoolean(key, defValue)

    fun getBytes(key: String, defValue: ByteArray? = null) = kv.getBytes(key, defValue)

    fun getFloat(key: String, defValue: Float = 0f) = kv.getFloat(key, defValue)

    fun getInt(key: String, defValue: Int = 0) = kv.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0L) = kv.getLong(key, defValue)

    fun getString(key: String, defValue: String? = null) = kv.getString(key, defValue)

}