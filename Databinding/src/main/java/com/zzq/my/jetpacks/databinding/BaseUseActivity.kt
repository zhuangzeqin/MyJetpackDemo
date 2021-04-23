package com.zzq.my.jetpacks.databinding

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.zzq.my.jetpacks.databinding.databinding.ActivityBaseUseBinding
import com.zzq.my.jetpacks.tools.BindHelp
import java.util.*

/*
  * ================================================
  * 描述：dataBinding的基础用法演示界面
  * 作者：zhuangzeqin
  * 时间: 2021/4/21-13:47
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
  *
   DataBinding使用步骤简要：
     1、使用最新版的AndroidStudio，至少AS3.0以上吧。
     2、在项目module下的build.gradle的android闭包下，配置 databinding{enabled=true}
     3、对于布局的xml文件，将原有的正常布局，外面用<layout></layout>包裹作为跟节点。<data></data>节点下存放用于xml布局的一些变量，工具类之类的
     4、在代码无误的情况下，build一下module或整个project。然后就可以在代码中使用binding方式coding了。
  */
class BaseUseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringJoiner = StringJoiner(",")
        //Fragment 获取方式  xxxbinding.inflater()
        val binding = DataBindingUtil.setContentView<ActivityBaseUseBinding>(this, R.layout.activity_base_use)
        binding.apply {
            //设置在xml中声明的变量值
            binding.age = 10
            binding.isStudent = true
            binding.name = "BindName"
            binding.title = "BD标题"
            //list,map,这里的ages和map，赋值给xml中的变量
            val ages = listOf("20", "18", "19")
            val map = mapOf(19 to "Lily", 21 to "Jim", 20 to "Aili")
            binding.ages = ages
            binding.map = map
            //静态点击的helper
            binding.helper = BindHelp
        }
    }
}