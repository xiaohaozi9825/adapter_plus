package com.iflytek.myapplication;

import com.iflytek.myapplication.bean.FriendInfo;
import com.iflytek.myapplication.databinding.ItemFriendBinding;

import pw.xiaohaozi.zkr.adapter.SingleTypeAdapter;
import pw.xiaohaozi.zkr.holder.ViewHolder;


class FriendAdapter extends SingleTypeAdapter<ItemFriendBinding, FriendInfo, ViewHolder<ItemFriendBinding>> {


    @Override
    protected void onBindViewHolder(int i, ViewHolder<ItemFriendBinding> itemFriendBindingViewHolder) {
        itemFriendBindingViewHolder.getBinding().setFriendInfo(getDataList().get(i));
    }
}
