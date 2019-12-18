package pw.xiaohaozi.zkr.listener;

import androidx.databinding.ViewDataBinding;

public interface OnSelectListener<VDB extends ViewDataBinding> {
    /**
     * 选中事件，当某item被选中时触发
     *
     * @param vdb
     */
    void onSelectk(VDB vdb);
}
