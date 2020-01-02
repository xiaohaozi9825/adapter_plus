package com.iflytek.myapplication.holder;

import com.iflytek.myapplication.databinding.ItemFriendBinding;

import androidx.annotation.NonNull;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

public class MySingelTypeHolder extends ViewHolder<ItemFriendBinding> {
        public MySingelTypeHolder(@NonNull ItemFriendBinding binding) {
            super(binding);
            //为头像添加点击事件和长按事件
            binding.ivHeadPortrait.setOnClickListener(this);
            binding.ivHeadPortrait.setOnLongClickListener(this);
        }
    }
