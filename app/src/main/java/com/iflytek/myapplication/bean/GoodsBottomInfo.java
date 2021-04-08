package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import pw.xiaohaozi.adapter_plus.data.ViewTyper;

public class GoodsBottomInfo extends BaseObservable implements ViewTyper {
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
    public int getItemViewType() {
        return 2;
    }
}
