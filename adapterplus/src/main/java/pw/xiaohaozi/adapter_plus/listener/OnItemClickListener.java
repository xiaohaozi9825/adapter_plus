package pw.xiaohaozi.adapter_plus.listener;

import androidx.databinding.ViewDataBinding;

public interface OnItemClickListener<VDB extends ViewDataBinding> {
    /**
     * item被点击时触发
     *
     * @param vdb 如果想获取被点击的View，可使用binding.getRoot()方法
     */
    void onItemClick(VDB vdb);
}
