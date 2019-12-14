package com.iflytek.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * 好友信息
 */
public class FriendInfo extends BaseObservable {
    private String name;
    private String headPortrait;
    private String ana;

    public FriendInfo(String name, String headPortrait, String ana) {
        this.name = name;
        this.headPortrait = headPortrait;
        this.ana = ana;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
        notifyPropertyChanged(BR.headPortrait);
    }

    @Bindable
    public String getAna() {
        return ana;
    }

    public void setAna(String ana) {
        this.ana = ana;
        notifyPropertyChanged(BR.ana);
    }
}
