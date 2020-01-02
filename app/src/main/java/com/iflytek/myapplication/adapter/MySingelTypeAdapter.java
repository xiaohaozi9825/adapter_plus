package com.iflytek.myapplication.adapter;

import com.iflytek.myapplication.bean.FriendInfo;
import com.iflytek.myapplication.databinding.ItemFriendBinding;
import com.iflytek.myapplication.holder.MySingelTypeHolder;

import pw.xiaohaozi.adapter_plus.adapter.SingleTypeAdapter;


/**
     * 好友列表适配器
     * 继承SingleTypeAdapter，可自定义viewholder
     */
    public class MySingelTypeAdapter extends SingleTypeAdapter<ItemFriendBinding, FriendInfo,
                MySingelTypeHolder> {

        @Override
        protected void onBindViewHolder(int position, MySingelTypeHolder viewHolder) {
            viewHolder.getBinding().setFriendInfo(getDataList().get(position));
        }
    }
