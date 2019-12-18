package com.iflytek.myapplication;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.zkr.holder.ViewHolder;

public class SelectHolder<VDB extends ViewDataBinding> extends ViewHolder<VDB> {
    public SelectHolder(@NonNull VDB binding) {
        super(binding);
        Log.i("SelectHolder", "SelectHolder: ");
    }
}
