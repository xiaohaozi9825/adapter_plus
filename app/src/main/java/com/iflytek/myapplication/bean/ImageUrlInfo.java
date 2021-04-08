package com.iflytek.myapplication.bean;

import pw.xiaohaozi.adapter_plus.adapter.Check;

/**
 * 描述：
 * 作者：小耗子
 * 简书地址：https://www.jianshu.com/u/2a2ea7b43087
 * github：https://github.com/xiaohaozi9825
 * 创建时间：2020/7/21 0021 17:39
 */
public class ImageUrlInfo implements Check {
    private boolean selected;
    private String usl;

    public ImageUrlInfo(String usl) {
        this.usl = usl;
    }

    @Override
    public boolean isSelected___() {
        return selected;
    }

    @Override
    public void setSelected___(boolean selected___) {
        selected = selected___;
    }

    public String getUsl() {
        return usl;
    }

    public void setUsl(String usl) {
        this.usl = usl;
    }
}
