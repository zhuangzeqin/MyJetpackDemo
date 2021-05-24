package com.zzq.my.jetpacks.common.net.exception

import com.zzq.my.jetpacks.common.net.base.BaseResponse
import com.zzq.my.jetpacks.common.utils.Status


/*
  * ================================================
  * 描述：自定义异常
  * 作者：zhuangzeqin
  * 时间: 2021/5/21-15:50
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
class ResponseThrowable : Exception {
    var code: Int//错误码
    var errMsg: String//错误信息

    constructor(code: Int, errMsg: String) {
        this.code = code
        this.errMsg = errMsg
    }

    constructor(status: Status, e: Throwable? = null) : super(e) {
        code = status.code
        errMsg = status.msg
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }

    constructor(base: BaseResponse<*>, e: Throwable? = null) : super(e) {
        this.code = base.code
        this.errMsg = base.message
    }


}

