package com.iflytek.myapplication.adapter;

import android.view.ViewGroup;

import com.iflytek.myapplication.bean.FriendInfo;
import com.iflytek.myapplication.databinding.ItemFriendBinding;
import com.iflytek.myapplication.holder.MySingelTypeHolder;

import androidx.annotation.NonNull;
import pw.xiaohaozi.adapter_plus.adapter.SingleTypeAdapter;


/**
 * 好友列表适配器
 * 继承SingleTypeAdapter，可自定义viewholder
 */
public class MySingelTypeAdapter extends SingleTypeAdapter<ItemFriendBinding, FriendInfo, MySingelTypeHolder> {


    @Override
    protected void onBindViewHolder(@NonNull MySingelTypeHolder mySingelTypeHolder, int position, @NonNull ItemFriendBinding itemFriendBinding, @NonNull FriendInfo friendInfo, int isSelect) {
        itemFriendBinding.setFriendInfo(friendInfo);
    }
}
