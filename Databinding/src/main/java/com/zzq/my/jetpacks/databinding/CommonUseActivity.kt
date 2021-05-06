package com.zzq.my.jetpacks.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zzq.my.jetpacks.adapter.*
import com.zzq.my.jetpacks.bean.CommonUser
import com.zzq.my.jetpacks.bean.MultiMsg
import com.zzq.my.jetpacks.databinding.databinding.ActivityCommonUseBinding

class CommonUseActivity : AppCompatActivity() {
    private lateinit var ladapter: LVCommonUserAdapter
    private lateinit var rvadapter: RVCommonUserAdapter
    private lateinit var ladapterMulti: LVCommonUserMultiAdapter
    private lateinit var radapterMulti: RVCommonUserMultiAdapter
    private var userlist:MutableList<CommonUser>? =  null
    private var multiMsgList:MutableList<MultiMsg>? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关联布局，binding对象
        val binding = DataBindingUtil.setContentView<ActivityCommonUseBinding>(
            this,
            R.layout.activity_common_use
        )
//        singAdapter(binding)
//        singAdapter2(binding)
        singAdapter3(binding)
        singAdapter4(binding)
    }

    private fun singAdapter(binding: ActivityCommonUseBinding) {
        userlist = mutableListOf()
        for (i in 0..20) {
            userlist!!.add(CommonUser("zzq${i}", 32, 1, "我是一个合格的程序员$i"))
        }
        ladapter = LVCommonUserAdapter(this)
        ladapter.setList(userlist)
        binding.ladapter = ladapter
    }

    private fun singAdapter2(binding: ActivityCommonUseBinding) {
        multiMsgList = mutableListOf()
        for (i in 0..20) {
            multiMsgList!!.add(MultiMsg(MultiMsg.Message(i, "zzq$i","1", "150$i"),MultiMsg.Meta("消息$i",i)))
        }
        ladapterMulti = LVCommonUserMultiAdapter(this)
        ladapterMulti.setList(multiMsgList)
        binding.ladapterMultiAdapter = ladapterMulti
    }

    private fun singAdapter3(binding: ActivityCommonUseBinding) {
        userlist = mutableListOf()
        for (i in 0..20) {
            userlist!!.add(CommonUser("zzq${i}", 32, 1, "我是一个合格的程序员$i"))
        }
        rvadapter = RVCommonUserAdapter(this)
        rvadapter.setList(userlist)
        binding.radapter = rvadapter
    }


    private fun singAdapter4(binding: ActivityCommonUseBinding) {
        multiMsgList = mutableListOf()
        for (i in 0..20) {
            multiMsgList!!.add(MultiMsg(MultiMsg.Message(i, "zzq$i","1", "150$i"),MultiMsg.Meta("消息$i",i)))
        }
        radapterMulti = RVCommonUserMultiAdapter(this)
        radapterMulti.setList(multiMsgList)
        binding.radapterMultiAdapter = radapterMulti
    }
}