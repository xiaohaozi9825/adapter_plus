package com.iflytek.myapplication;

import android.util.Log;

import com.iflytek.myapplication.bean.FriendInfo;
import com.iflytek.myapplication.databinding.ItemFriendBinding;

import pw.xiaohaozi.zkr.adapter.SelectAdapter;


public class MyAdapter extends SelectAdapter<ItemFriendBinding, FriendInfo, SelectHolder<ItemFriendBinding>> {

    @Override
    protected void onSelectChange(int position, boolean isSelect) {
        if (isSelect) {
            Log.i("选择", "onSelectChange: +++ " + position);
        } else {
            Log.i("选择", "onSelectChange: --- " + position);
        }
    }

    @Override
    protected void onBindViewHolder(SelectHolder<ItemFriendBinding> itemFriendBindingSelectHolder, int i, boolean b) {
        itemFriendBindingSelectHolder.getBinding().setFriendInfo(getDataList().get(i));
        itemFriendBindingSelectHolder.getBinding().getRoot().setBackgroundColor(b ? 0x88ff0000 : 0x8800ff00);
    }

}
