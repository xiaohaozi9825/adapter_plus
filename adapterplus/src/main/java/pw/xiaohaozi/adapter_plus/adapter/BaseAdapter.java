package pw.xiaohaozi.adapter_plus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.listener.OnItemLongClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnItemClickListener;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;
import pw.xiaohaozi.adapter_plus.listener.OnLongClickListener;

/**
 * 该类对RecyclerView.Adapter做了进一步封装，使其使用更加简单方便
 *
 * @param <VDB>
 * @param <D>
 * @param <VH>
 */
public abstract class BaseAdapter<VDB extends ViewDataBinding, D, VH extends ViewHolder<VDB>> extends RecyclerView.Adapter {
    private List<D> mDataList;
    protected Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnClickListener mOnClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnLongClickListener mOnLongClickListener;

    /**
     * 追加数据，可用于上拉加载更多
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param list
     */
    @Deprecated
    public boolean add(List<D> list) {
        if (mDataList == null) {
            return refresh(list);
        } else {
            return mDataList.addAll(list);
        }
    }

    /**
     * 增加一个数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param data
     */
    @Deprecated
    public boolean add(D data) {
        if (mDataList == null) {
            mDataList = new ObservableArrayList<>();
            mDataList.add(data);
            return refresh(mDataList);
        } else {
//            if (!mDataList.contains(data)) {
//                mDataList.add(data);
//            }

            if (mDataList instanceof ObservableList)
                return mDataList.add(data);
            else {
                boolean add = mDataList.add(data);
                if (add) notifyItemInserted(mDataList.size() - 1);
                return add;
            }


        }
    }

    /**
     * 增加一条数据到指定位置
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param position
     * @param data
     */
    @Deprecated
    public boolean add(int position, D data) {
        if (mDataList == null) {
            mDataList = new ObservableArrayList<>();
            mDataList.add(data);
            return refresh(mDataList);
        } else {
            mDataList.add(position, data);
            if (!(mDataList instanceof ObservableList)) notifyItemInserted(position);
            return true;
        }
    }

    /**
     * 移除指定位置数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param position
     */
    @Deprecated
    public boolean remove(int position) {
        if (mDataList == null) return false;
        D d = mDataList.remove(position);
        return d != null;
    }

    /**
     * 移除指定数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param data
     */
    @Deprecated
    public boolean remove(D data) {
        if (mDataList == null) return false;
        if (data == null) return false;
        return mDataList.remove(data);
    }

    /**
     * 移除所有数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     */
    @Deprecated
    public boolean remove() {
        if (mDataList == null) return false;
        mDataList.clear();
        return true;
    }

    /**
     * 刷新数据，可用于第一次加载数据或下拉刷新
     *
     * @param list
     */
    public boolean refresh(List<D> list) {
        if (list == null) return false;
        mDataList = list;
        if (mDataList instanceof ObservableList) {
            ((ObservableList) mDataList).addOnListChangedCallback(new DynamicChangeCallback(this));
        }
        notifyDataSetChanged();
        return true;
    }

    /**
     * item点击事件，内部已集成好了
     *
     * @param listener
     */
    public <A extends BaseAdapter> A setOnItemClickListener(OnItemClickListener<VDB> listener) {
        mOnItemClickListener = listener;
        return (A) this;
    }

    /**
     * 指定view点击事件，这里只提供设置方法和回调接口
     * 具体需要继承ViewHolder并为需要被点击的view添加点击事件
     *
     * @param listener
     */
    public <A extends BaseAdapter> A setOnClickListener(OnClickListener<VDB> listener) {
        mOnClickListener = listener;
        return (A) this;
    }

    public <A extends BaseAdapter> A setOnItemLongClickListener(OnItemLongClickListener<VDB> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
        return (A) this;
    }

    public <A extends BaseAdapter> A setOnLongClickListener(OnLongClickListener<VDB> onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
        return (A) this;
    }

    public List<D> getDataList() {
        return mDataList;
    }

    @NonNull
    @Override
    final public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        VDB binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getLayoutRes(viewType), parent,
                false);
        VH vh = onCreateViewHolder(parent, binding, viewType);
        if (mOnItemClickListener != null) vh.setOnItemClickListener(mOnItemClickListener);
        if (mOnClickListener != null) vh.setOnClickListener(mOnClickListener);
        if (mOnItemLongClickListener != null)
            vh.setOnItemLongClickListener(mOnItemLongClickListener);
        if (mOnLongClickListener != null) vh.setOnLongClickListener(mOnLongClickListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        onBindViewHolder((VH) viewHolder, position, ((VH) viewHolder).getBinding(), mDataList.get(position));
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   如果该adapter是给RecyclerView用的，则parent就是RecyclerView类型
     * @param binding
     * @param viewType
     * @return
     */
    protected abstract <VG extends ViewGroup> VH onCreateViewHolder(@NonNull VG parent, VDB binding, int viewType);

    protected abstract void onBindViewHolder(VH vh, int position, VDB vdb, D d);

    protected abstract int getLayoutRes(int viewType);

    @Override
    final public int getItemViewType(int position) {
        D d = mDataList.get(position);
        if (d instanceof RecyclerData) {
            return ((RecyclerData) d).get_RV_ItemViewType();
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    final public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    /**
     * 动态更新列表
     * 当ObservableList数据变化时动态更新ui
     *
     * @param <T>
     */
    final private class DynamicChangeCallback<T extends BaseAdapter> extends
            ObservableList.OnListChangedCallback<ObservableList<T>> {

        private RecyclerView.Adapter adapter;

        public DynamicChangeCallback(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onChanged(ObservableList<T> sender) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            adapter.notifyItemRangeRemoved(fromPosition, itemCount);
            adapter.notifyItemRangeInserted(toPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

}
