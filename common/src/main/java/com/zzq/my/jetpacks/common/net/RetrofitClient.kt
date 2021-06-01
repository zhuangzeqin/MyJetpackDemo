package com.zzq.my.jetpacks.common.net


import com.zzq.my.jetpacks.common.net.interceptors.KtHttpLogInterceptor
import com.zzq.my.jetpacks.common.net.interceptors.RequestInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述：RetrofitClient 配置信息类
 * 作者：zhuangzeqin
 * 时间: 2021/5/21-9:23
 * 邮箱：zzq@eeepay.cn
 * 备注: 采用的是kt 单例模式
 */
private const val TAG = "RetrofitClient"
class RetrofitClient {
    companion object {
        private lateinit var retrofit: Retrofit
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    init {
        //初始化配置
        initConfig()
    }

    /**
     * 初始化配置
     * [baseUrl]项目接口的基类url，以/结尾
     */
    fun initConfig(
        baseUrl: String = UrlConfig.TEST_BASEURL,
        okClient: OkHttpClient = getOkHttpClient()
    ): RetrofitClient {
        retrofit = Retrofit.Builder()
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
        return this
    }

    /**
     * 创建okhttpClient 对象
     */
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS) //完整的请求超时时长，从发起请求到接收返回数据
            .connectTimeout(10, TimeUnit.SECONDS) //与服务器建立连接时长
            .readTimeout(10, TimeUnit.SECONDS) //读取服务器返回数据的时长
            .writeTimeout(10, TimeUnit.SECONDS) //向服务器写入数据的时长
            .retryOnConnectionFailure(true)//是否重连
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
//            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
//                override fun log(message: String) {
//                    Log.d(TAG, "log: $message")
//                }
//            }).setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addNetworkInterceptor(RetryInterceptor(1))//重试1次
            .addInterceptor(KtHttpLogInterceptor(){
                setTag("HttpLog")
                setLogColor(KtHttpLogInterceptor.LogColor.INFO)
                setLogLevel(KtHttpLogInterceptor.LogLevel.BODY)
            })

            .addNetworkInterceptor(RequestInterceptor())//
//            .addInterceptor(LoggingInterceptor2())
            .build()
    }

    /**
     * 创建retrofit的Service对象
     * [service] 定义的retrofit的service 接口类
     */
    fun <T> create(service: Class<T>): T = retrofit.create(service)

}