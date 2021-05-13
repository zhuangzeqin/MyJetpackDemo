package com.zzq.my.jetpacks.tools

/**
 * 描述：配置类常用的写法
 * 作者：zhuangzeqin
 * 时间: 2021/5/13-15:50
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class ConfigBuilder(block: (ConfigBuilder.() -> Unit)? = null) {
    private var mTag = ""
    private var mUrl = ""

    init {
        block?.invoke(this)
    }

    fun setTag(str: String): ConfigBuilder {
        this.mTag = str
        return this
    }

    fun setBaseUrl(url: String): ConfigBuilder {
        this.mUrl = url
        return this
    }

    fun startConfig() {
        System.out.println("AAAAAAAAAAAAA$mTag$mUrl")
    }
}