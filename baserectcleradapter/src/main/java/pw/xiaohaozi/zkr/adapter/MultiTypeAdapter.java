package pw.xiaohaozi.zkr.adapter;

import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.zkr.holder.ViewHolder;

/**
 * 多类型viewType
 * 1、传入的Javabean必须实现RecyclerData接口
 */
public abstract class MultiTypeAdapter extends BaseAdapter<ViewDataBinding, RecyclerData, ViewHolder<ViewDataBinding>> {
    @Override
    protected ViewHolder<ViewDataBinding> onCreateViewHolder(ViewDataBinding binding, int viewType) {
        return new ViewHolder<>(binding);
    }
}
