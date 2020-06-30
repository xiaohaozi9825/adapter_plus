package pw.xiaohaozi.adapter_plus.listener;

import android.view.View;

import androidx.databinding.ViewDataBinding;

public interface OnItemLongClickListener<VDB extends ViewDataBinding> {
    /**
     * item被长按时触发
     *
     * @param v
     * @param vdb      如果想获取被点击的View，可使用binding.getRoot()方法
     * @param position
     */
    void onItemLongClick(View v, VDB vdb, int position);
}
