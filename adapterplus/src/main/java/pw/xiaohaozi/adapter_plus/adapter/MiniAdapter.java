package pw.xiaohaozi.adapter_plus.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * 描述：
 * 作者：小耗子
 * 简书地址：https://www.jianshu.com/u/2a2ea7b43087
 * github：https://github.com/xiaohaozi9825
 * 创建时间：2021/4/20 10:44
 */
public class MiniAdapter<VDB extends ViewDataBinding, D> extends SimpleAdapter<VDB, D> {
    private IBind<D, VDB> mIBind;
    private ICreate<VDB> mICreate;

    public MiniAdapter(ICreate<VDB> ICreate, @NonNull IBind<D, VDB> IBind) {
        mIBind = IBind;
        mICreate = ICreate;
    }

    public MiniAdapter(@NonNull IBind<D, VDB> IBind) {
        mIBind = IBind;
    }

    @Override
    protected <VG extends ViewGroup> ViewHolder<VDB> onCreateViewHolder(@NonNull VG parent, @NonNull VDB binding, int viewType) {
        ViewHolder<VDB> holder = super.onCreateViewHolder(parent, binding, viewType);
        if (mICreate != null) mICreate.created(holder);
        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<VDB> holder, int position, @NonNull VDB binding, @Nullable D data, int checkIndex) {
        if (mIBind != null) mIBind.bind(binding, data, checkIndex);
    }

    public interface ICreate<VDB extends ViewDataBinding> {
        void created(@NonNull ViewHolder<VDB> holder);
    }

    public interface IBind<D, VDB extends ViewDataBinding> {
        void bind(@NonNull VDB binding, @Nullable D data, int checkIndex);
    }
}
