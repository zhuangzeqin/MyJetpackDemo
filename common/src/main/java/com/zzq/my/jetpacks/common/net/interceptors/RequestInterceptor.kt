package com.zzq.my.jetpacks.common.net.interceptors

import android.annotation.SuppressLint
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.provider.Settings
import com.blankj.utilcode.util.Utils
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.zzq.my.jetpacks.common.bean.AppDeviceInfo
import com.zzq.my.jetpacks.common.utils.Md5
import com.zzq.my.jetpacks.common.utils.SpUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * 描述：加密请求参数签名规则构建拦截器
 * 默认所有的接口都需要签名
 * 签名规则
 * 获取request body的内容
 * 如果有loginToken,这加上,否则忽略
 * app-info 公共参数必须还有当前请求的时间戳(key为timestamp),要参与签名
 * 把上面的参数按字典值正序排序
 * 然后使用key=value&方式拼接起来
 * 再拼接的字符串后加上key=46940880d9f79f27bb7f85ca67102bfdylkj@@agentapi2#$$^&pretty
 * 最后对拼接完成的字符串进行md5签名,然后以sign为key放到请求head中
 * 参数值为空的话忽略
 * 参考登陆接口的api文档说明
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-14:18
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
class RequestInterceptor : Interceptor {
    companion object {
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
        private val mapType = object : TypeToken<Map<String, Any>>() {}.type
    }

    //再拼接的字符串后加上key=46940880d9f79f27bb7f85ca67102bfdylkj@@agentapi2#$$^&pretty
    private val KEY_VALUE = "key=46940880d9f79f27bb7f85ca67102bfdylkj@@agentapi2#$$^&pretty"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val rootMap = TreeMap<String, String>()
        // 公共请求参数
        val attachHeaders = mutableListOf<Pair<String, String>>(
//            "appid" to NET_CONFIG_APP_ID,
//            "platform" to "android",//如果重复请求，可能会报重复签名错误，yapi 平台标记则不会
//            "timestamp" to System.currentTimeMillis().toString(),
//            "brand" to DeviceUtils.getManufacturer(),
//            "model" to DeviceUtils.getModel(),
//            "uuid" to DeviceUtils.getUniqueDeviceId(),
//            "network" to NetworkUtils.getNetworkType().name,
//            "system" to DeviceUtils.getSDKVersionName(),
//            "version" to AppUtils.getAppVersionName()
        )
        // 需要加密的请求参数 公共请求参数加上原本的请求参数
        val signHeaders = mutableListOf<Pair<String, String>>()
//        signHeaders.addAll(attachHeaders)
        //get的请求，参数
        if (originRequest.method == "GET") {
            originRequest.url.queryParameterNames.forEach { key ->
//                signHeaders.add(key to (originRequest.url.queryParameter(key) ?: ""))
                rootMap[key] = originRequest.url.queryParameter(key) ?: ""
            }
        }
        val requestBody = originRequest.body
        if ("POST" == originRequest.method) {
            // post请求需要将内部的字段遍历出来参与sign计算
            if (requestBody is FormBody) {
                for (i in 0 until requestBody.size) {
//                    signHeaders.add(requestBody.encodedName(i) to requestBody.encodedValue(i))
                    rootMap[requestBody.encodedName(i)] = requestBody.encodedValue(i)
                }
            }
            // json形式的body，将json转成Map遍历
            if (requestBody?.contentType()?.type == "application" && requestBody.contentType()?.subtype == "json") {
                runCatching {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    buffer.readByteString().utf8()
                }.onSuccess {
                    val map = gson.fromJson<Map<String, Any>>(it, mapType)
                    map.forEach { p ->
//                        signHeaders.add(p.key to p.value.toString())
                        rootMap[p.key] = p.value.toString()
                    }
                }
            }
        }

        val timestamp = System.currentTimeMillis().toString() //时间戳
//        signHeaders.add(Pair("timestamp", timestamp))
        rootMap.put("timestamp",timestamp)
        val loginToken = SpUtils.getString("token", "")//登录的token
//        signHeaders.add(Pair("loginToken", loginToken.toString()))
        rootMap.put("loginToken", loginToken.toString())
        //1 遍历map key拼接  key=value&
        //2 value 空的不拼接
        var signValue = ""
        rootMap.forEach{
                p-> signValue = signValue + p.key + "=" + p.value + "&" }
        signValue.plus("&appkey=$KEY_VALUE")
        // 加密的请求参数按照Ascii排序 用&拼接加上"&appkey=appKeyValue"
//        val signValue = signHeaders
//            .sortedBy { it.first }
//            .joinToString("&") { "${it.first}=${it.second}" }
//            .plus("&appkey=$KEY_VALUE")
        //最后对拼接完成的字符串进行md5签名,然后以sign为key放到请求body中
//        val sign = EncryptUtils.encryptMD5ToString(signValue)
        val sign = Md5.encode(signValue)
        //重新组装 装换成json字符串
        val appDeviceInfo = getAppDeviceInfo(sign, timestamp)
        val newRequest = originRequest.newBuilder()
//            .cacheControl(CacheControl.FORCE_NETWORK)
//        attachHeaders.forEach {
//            newRequest.header(it.first, it.second)
//        }

        // 添加签名信息
        appDeviceInfo?.let { newRequest.header("app-info", it) }
        newRequest.header("Content-Type", "application/json; charset=UTF-8")
        newRequest.header("Connection", "keep-alive")
        newRequest.header("Accept", "*/*")
        return chain.proceed(newRequest.build())
    }

    /**
     * 公共参数转换为json 字符串
     *
     * @return
     */
    private fun getAppDeviceInfo(sign: String, timestamp: String): String? {
        return try {
            val appDeviceInfo = AppDeviceInfo()
            // app的项目名比如盛代宝后面有可能是OEM
            appDeviceInfo.setAppName("盛代宝")
            appDeviceInfo.setName(Build.MODEL) // 型号 Mi 5s plus
            appDeviceInfo.setSystemName("android") // android / ios
            appDeviceInfo.setSystemVersion(Build.VERSION.RELEASE) //系统版本 8.0.0
            appDeviceInfo.setDeviceId(getDeviceId()) //设备id
            appDeviceInfo.setAppVersion(getVersionName()) //app版本
            appDeviceInfo.setAppBuild(getUUID()) //app构建版本
            val appNo = "2" // app的标志
            val agentOem = "200010" //新增组织id头部固定上送
            appDeviceInfo.setAppNo(appNo)
            appDeviceInfo.setAgentOem(agentOem) //组织ID---
            appDeviceInfo.setAppChannel(appNo) //app渠道---
            val loginToken = SpUtils.getString("token", "")//登录的token
            //        String loginToken = PreferenceUtils.getStringParam(ConfigPathConstants.SP_LOGINTOKEN, "unknow");
            appDeviceInfo.setLoginToken(loginToken) //登陆token
            appDeviceInfo.setTimestamp(timestamp) //当前的时间戳
            appDeviceInfo.setSign(sign)
            appDeviceInfo.setJpushDevice("10000") //获取极光推送的注册的id
            val jsonData: String = gson.toJson(appDeviceInfo) //公共参数转换为json 字符串
            //最好编码一下； 防止乱码
            URLEncoder.encode(jsonData, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 返回安卓设备ID
     */
    @SuppressLint("HardwareIds")
    private fun getDeviceId(): String? {
        var id: String? = "NO Search"
        id = Settings.Secure.getString(
            Utils.getApp().contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return id
    }

    // 获得软件版本名
    private fun getVersionName(): String? {
        var versionname: String? = "unknow"
        try {
            versionname = Utils.getApp().packageManager.getPackageInfo(
                Utils.getApp().packageName, 0
            ).versionName
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        return versionname
    }

    /**
     * 获取32位UUID
     *
     * @return
     */
    fun getUUID(): String? {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }
}