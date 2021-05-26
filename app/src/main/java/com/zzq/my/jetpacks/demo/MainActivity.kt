package com.zzq.my.jetpacks.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.logger.Logger
import com.zzq.my.jetpacks.common.bean.LoginInfo
import com.zzq.my.jetpacks.common.net.base.observe
import com.zzq.my.jetpacks.demo.login.LoginModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginModel::class.java)

        val  parameters = mutableMapOf("userName" to "19000000021","password" to "abc888888","agentOem" to "200010")
        parameters.put("agent_no", "23302") //当前登录代理商编号,必填
        parameters.put("agentNo", "23302") //当前登录代理商编号,必填 后台定义的字段不一样
        parameters.put("curAgentNo", "23302") //当前登录代理商编号,必填 后台定义的字段不一样
        loginModel.reqLogin(parameters)
        loginModel.banners.observe(this,{dataBean: LoginInfo.DataBean? ->
            Logger.t("loginModel").d("loginModel${dataBean.toString()}")
        },{i: Int, s: String? ->
            Logger.t("loginModel").d("loginModel:$s")
        })

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}