package com.zzq.my.jetpacks.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zzq.my.jetpacks.adapter.LVCommonUserAdapter
import com.zzq.my.jetpacks.adapter.ListviewAdapterTest
import com.zzq.my.jetpacks.bean.CommonUser
import com.zzq.my.jetpacks.databinding.databinding.ActivityCommonUseBinding

class CommonUseActivity : AppCompatActivity() {
    private lateinit var ladapter: LVCommonUserAdapter
    private var userlist:MutableList<CommonUser>? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关联布局，binding对象
        val binding = DataBindingUtil.setContentView<ActivityCommonUseBinding>(
            this,
            R.layout.activity_common_use
        )
        userlist = mutableListOf()
        for (i in 0..20)
        {
            userlist!!.add(CommonUser("zzq${i}",32,1, "我是一个合格的程序员$i"))
        }
        ladapter = LVCommonUserAdapter(this)
        ladapter.setList(userlist)
        binding.ladapter =ladapter

    }
}