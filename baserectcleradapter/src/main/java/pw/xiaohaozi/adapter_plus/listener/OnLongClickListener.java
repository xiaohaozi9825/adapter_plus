package pw.xiaohaozi.adapter_plus.listener;

import android.view.View;

import androidx.databinding.ViewDataBinding;

public interface OnLongClickListener<VDB extends ViewDataBinding> {
    /**
     * View 点击事件
     *
     * @param view 被点击的View
     * @param vdb
     */
    void onLongClick(View view, VDB vdb);
}
