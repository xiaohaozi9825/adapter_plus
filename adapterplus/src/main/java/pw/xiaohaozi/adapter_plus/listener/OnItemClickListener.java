package pw.xiaohaozi.adapter_plus.listener;

import android.view.View;

import androidx.databinding.ViewDataBinding;

public interface OnItemClickListener<VDB extends ViewDataBinding> {
    /**
     * item被点击时触发
     *
     * @param v
     * @param vdb 如果想获取被点击的View，可使用binding.getRoot()方法
     */
    void onItemClick(View v, VDB vdb, int position);
}
