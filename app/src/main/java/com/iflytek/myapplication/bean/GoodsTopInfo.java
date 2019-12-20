package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import pw.xiaohaozi.zkr.adapter.RecyclerData;

public class GoodsTopInfo extends BaseObservable implements RecyclerData {
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
    public int get_RV_ItemViewType() {
        return 0;
    }
}
