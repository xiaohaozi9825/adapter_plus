package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import pw.xiaohaozi.adapter_plus.data.ViewTyper;

public class GoodsTopInfo extends BaseObservable implements ViewTyper {
  private   String storeName;

    public GoodsTopInfo(String storeName) {
        this.storeName = storeName;
    }

    @Bindable
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
        notifyPropertyChanged(BR.storeName);
    }

    @Override
    public int getItemViewType() {
        return 0;
    }
}
