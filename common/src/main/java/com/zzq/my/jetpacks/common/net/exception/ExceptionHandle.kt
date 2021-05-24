package com.zzq.my.jetpacks.common.net.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.zzq.my.jetpacks.common.utils.StatusUtils
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/*
  * ================================================
  * 描述：统一异常处理
  * 作者：zhuangzeqin
  * 时间: 2021/5/21-16:33
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
object ExceptionHandle {

    fun handleException(throwable: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        // 自定义异常
        if (throwable is ResponseThrowable) {
            ex = throwable
        }
        // 其他各种异常
        else if (throwable is HttpException) {
            ex = ResponseThrowable(
                StatusUtils.HTTP_ERROR,
                throwable
            )
        } else if (throwable is JsonParseException
            || throwable is JSONException
            || throwable is ParseException || throwable is MalformedJsonException
        ) {
            ex = ResponseThrowable(
                StatusUtils.PARSE_ERROR,
                throwable
            )
        } else if (throwable is ConnectException || throwable is UnknownHostException) {
            ex = ResponseThrowable(
                StatusUtils.NETWORD_ERROR,
                throwable
            )
        } else if (throwable is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(
                StatusUtils.SSL_ERROR,
                throwable
            )
        } else if (throwable is java.net.SocketTimeoutException) {
            ex = ResponseThrowable(
                StatusUtils.TIMEOUT_ERROR,
                throwable
            )
        } else {
            ex = if (!throwable.message.isNullOrEmpty()) ResponseThrowable(
                1000,
                throwable.message!!,
                throwable
            )
            else ResponseThrowable(
                StatusUtils.UNKNOWN,
                throwable
            )
        }
        return ex
    }
}