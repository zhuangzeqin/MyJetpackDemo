package com.zzq.my.jetpacks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.zzq.my.jetpacks.bean.CommonUser;
import com.zzq.my.jetpacks.databinding.R;
import com.zzq.my.jetpacks.databinding.databinding.ItemLvBinding;

import java.util.List;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2021/4/21-17:52
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class ListviewAdapterTest extends BaseAdapter {
    private List<CommonUser> data;

    private Context context;

    public ListviewAdapterTest(List<CommonUser> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLvBinding binding;
//        ViewDataBinding binding;

        //ItemListBinding
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_lv, parent, false);
//            if (binding==null){
////                Log.e("空的binding");
//            }
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemLvBinding) convertView.getTag();
        }
        //ItemLvBinding 方式1

        binding.setUser(data.get(position));
        //数据ViewDataBinding  databingd 绑定 方式2
//        binding.setVariable(BR.user, data.get(position));
        return binding.getRoot();

    }
}
