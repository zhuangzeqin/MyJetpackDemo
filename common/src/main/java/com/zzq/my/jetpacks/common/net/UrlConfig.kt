package com.zzq.my.jetpacks.common.net
/**
 * 描述：环境的配置信息
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-10:27
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class UrlConfig {
    companion object {
        const val TEST_BASEURL = "http://cs-ys-agentapi2.51ydmw.com/"//测试环境
        const val OFFICIAL_BASEURL = "https://clientapi.liantb.com/" //生产环境地址
        const val QUASI_PRODUCTION_BASEURL= "https://clientapi-ltbtest.qhylpay.com/" //准生产
    }
}