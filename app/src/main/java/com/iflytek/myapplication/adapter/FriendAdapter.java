package com.iflytek.myapplication.adapter;

import com.iflytek.myapplication.bean.FriendInfo;
import com.iflytek.myapplication.databinding.ItemFriendBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;


/**
 * 好友列表适配器
 * 单类型最简单用法，只需要绑定数据即可
 * ViewHolder已经封装好了，不需要自己写了
 */
public class FriendAdapter extends SimpleAdapter<ItemFriendBinding, FriendInfo> {

    @Override
    protected void onBindViewHolder(ViewHolder<ItemFriendBinding> itemFriendBindingViewHolder,
                                    int position, ItemFriendBinding itemFriendBinding, FriendInfo friendInfo) {
        itemFriendBinding.setFriendInfo(friendInfo);
    }
}
