package pw.xiaohaozi.zkr.adapter.listener;

import android.view.View;

import androidx.databinding.ViewDataBinding;

public interface OnClickListener<VDB extends ViewDataBinding> {
    /**
     * View 点击事件
     * @param view 被点击的View
     * @param binding
     */
    void onClick(View view, VDB binding);
}
