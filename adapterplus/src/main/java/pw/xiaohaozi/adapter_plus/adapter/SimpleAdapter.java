package pw.xiaohaozi.adapter_plus.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * 最简单的Adapter（也是最常用的一个类）
 * itemType类型只能有一种，ViewHolder不需要重写的场景可用
 *
 * @param <VDB>
 * @param <D>
 */
public abstract class SimpleAdapter<VDB extends ViewDataBinding, D> extends SingleTypeAdapter<VDB, D, ViewHolder<VDB>> {
    /**
     * 父类SingleTypeAdapter中是使用反射创建ViewHolder的，对性能有一定影响
     * 而这里已经明确了ViewHolder的类型了，所以直接用new的方式创建
     *
     * @param binding
     * @param viewType
     * @return
     */
    @Override
    protected <VG extends ViewGroup> ViewHolder<VDB> onCreateViewHolder(@NonNull VG parent, @NonNull VDB binding, int viewType) {
        return new ViewHolder<>(binding);
    }
}
