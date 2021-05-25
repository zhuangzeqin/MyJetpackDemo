package com.zzq.my.jetpacks.common.net.base

import com.zzq.my.jetpacks.common.utils.StatusUtils

/**
 * 描述：用于封装回调到界面的响应结果
 * 作者：zhuangzeqin
 * 时间: 2021/5/25-15:15
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class RefurbishUIMessage<T> {
    /**
     * 响应code
     */
    var code = 0

    /**
     * 响应的msg
     */
    var msg = ""

    /**
     * 响应的数据
     */
    var data: T? = null

    /**
     * 响应的数据数量
     */
    var count = 0

    constructor(code: Int, msg: String) {
        this.code = code
        this.msg = msg
    }

    constructor(code: Int, data: T?) {
        this.code = code
        this.data = data
    }

    constructor(code: Int, data: T?, count: Int) {
        this.code = code
        this.data = data
        this.count = count
    }

    companion object {
        fun <T> getMessage(code: Int, msg: String): RefurbishUIMessage<T> {
            return RefurbishUIMessage<T>(code, msg)
        }

        fun <T> getMessage(code: Int, data: T?): RefurbishUIMessage<T> {
            return RefurbishUIMessage<T>(code, data)
        }

        fun <T> getMessage(code: Int, data: T?,count: Int): RefurbishUIMessage<T> {
            return RefurbishUIMessage<T>(code, data,count)
        }

        /**
         * 请求成功
         */
        fun <T> getSuccessMessage(data: T?): RefurbishUIMessage<T> {
            return getMessage<T>(StatusUtils.SUCCESS.code, data)
        }

        fun <T> getSuccessMessage(data: T?,count: Int): RefurbishUIMessage<T> {
            return getMessage<T>(StatusUtils.SUCCESS.code, data,count)
        }

        /**
         * 请求失败
         */
        fun <T> getFailMessage(msg: String): RefurbishUIMessage<T> {
            return getMessage<T>(StatusUtils.FAIL.code, msg)
        }

        /**
         * 请求完成
         */
        fun <T> getFinishMessage(): RefurbishUIMessage<T> {
            return getMessage<T>(StatusUtils.FINISH.code, "")
        }

    }
}