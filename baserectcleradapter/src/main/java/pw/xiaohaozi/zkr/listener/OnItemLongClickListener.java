package pw.xiaohaozi.zkr.listener;

import androidx.databinding.ViewDataBinding;

public interface OnItemLongClickListener<VDB extends ViewDataBinding> {
    /**
     * item被长按时触发
     *
     * @param vdb 如果想获取被点击的View，可使用binding.getRoot()方法
     */
    void onItemLongClick(VDB vdb);
}
