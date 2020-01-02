package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import pw.xiaohaozi.adapter_plus.adapter.RecyclerData;

public class GoodsBottomInfo extends BaseObservable implements RecyclerData {
    private String total;//合计多少钱

    public GoodsBottomInfo(String total) {
        this.total = total;
    }

    @Bindable
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    @Override
    public int get_RV_ItemViewType() {
        return 2;
    }
}
