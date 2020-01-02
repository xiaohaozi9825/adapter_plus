package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import pw.xiaohaozi.adapter_plus.adapter.RecyclerData;

public class GoodsCentreInfo extends BaseObservable implements RecyclerData {
    private String goodsImage;
    private String goodsName;
    private String goodsMsg;
    private String goodsPrice;

    public GoodsCentreInfo(String goodsImage, String goodsName, String goodsMsg, String goodsPrice) {
        this.goodsImage = goodsImage;
        this.goodsName = goodsName;
        this.goodsMsg = goodsMsg;
        this.goodsPrice = goodsPrice;
    }

    @Bindable
    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
        notifyPropertyChanged(BR.goodsImage);
    }

    @Bindable
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        notifyPropertyChanged(BR.goodsName);
    }

    @Bindable
    public String getGoodsMsg() {
        return goodsMsg;
    }

    public void setGoodsMsg(String goodsMsg) {
        this.goodsMsg = goodsMsg;
        notifyPropertyChanged(BR.goodsMsg);
    }

    @Bindable
    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
        notifyPropertyChanged(BR.goodsPrice);
    }

    @Override
    public int get_RV_ItemViewType() {
        return 1;
    }
}
