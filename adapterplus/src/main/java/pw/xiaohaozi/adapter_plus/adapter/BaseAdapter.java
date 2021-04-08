package pw.xiaohaozi.adapter_plus.adapter;


import android.content.Context;
import android.util.Log;
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
public abstract class BaseAdapter<VDB extends ViewDataBinding, D, VH extends ViewHolder<VDB>> extends RecyclerView.Adapter<VH> {
    private List<D> mDataList;
    protected Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnClickListener mOnClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnLongClickListener mOnLongClickListener;

    OnSelectChange<D> mOnSelectChange;
    int mMaxSelectSize = Integer.MAX_VALUE;//最多可以选中几个
    Warning mAutoRemoveWarning;
    boolean isAutoRemove = true;//当超出选中个数后是否自动删除最先选中的
    boolean isNoCancel = false;//是否禁止取消,当再次点击被选中的目标是，不执行任何操作
    /**
     * 追加数据，可用于上拉加载更多
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param list
     */
    @Deprecated
    public <A extends List<D>> boolean add(A list) {
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
    public <A extends List<D>> boolean refresh(A list) {
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
    public void setOnItemClickListener(OnItemClickListener<VDB> listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 指定view点击事件，这里只提供设置方法和回调接口
     * 具体需要继承ViewHolder并为需要被点击的view添加点击事件
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener<VDB> listener) {
        mOnClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<VDB> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener<VDB> onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public List<D> getDataList() {
        return mDataList;
    }

    @NonNull
    @Override
    final public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {
        Log.i("测试泛型", "onBindViewHolder: " + (mDataList.get(position) instanceof Check));
        onBindViewHolder(viewHolder, position, viewHolder.getBinding(), mDataList.get(position));
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   如果该adapter是给RecyclerView用的，则parent就是RecyclerView类型
     * @param binding
     * @param viewType
     * @return
     */
    protected abstract <VG extends ViewGroup> VH onCreateViewHolder(@NonNull VG parent, @NonNull VDB binding, int viewType);

    protected abstract void onBindViewHolder(@NonNull VH vh, int position, @NonNull VDB vdb, @NonNull D d);

    protected abstract int getLayoutRes(int viewType);

    @Override
    final public int getItemViewType(int position) {
        D d = mDataList.get(position);
        if (d instanceof RecyclerData) {
            return ((RecyclerData) d).getItemViewType();
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

    /**
     * 最多可以选择多少项
     * <p>
     * 默认Integer.MAX_VALUE
     * 如果想单选，则传入 1
     *
     * @param maxSelectSize
     * @return
     */
    public void setMaxSelectSize(int maxSelectSize) {
        mMaxSelectSize = maxSelectSize;
    }


    /**
     * 当选择个数大于 mMaxSelectSize 时，是否自动取消第一个选中的item
     *
     * @param autoRemove 默认true
     * @param warning    只有autoRemove==false 才能出发，当选中个数超过mMaxSelectSize时，
     *                   会回调warning.warn()方法。
     * @return
     */
    public void setAutoRemove(boolean autoRemove, Warning warning) {
        isAutoRemove = autoRemove;
        mAutoRemoveWarning = warning;
    }

    /**
     * 增加一条选中的item
     *
     * @param d
     * @return //
     */
    public abstract void addSelectItem(D d);

    /**
     * 取消选中状态
     *
     * @param d
     * @return
     */
    public abstract void cancelSelectItem(D d);


    /**
     * 全选
     *
     * @return
     */
    public abstract void selectAll();

    /**
     * 全不选
     *
     * @return
     */
    public abstract void cancelAll();

    /**
     * 反选
     *
     * @return
     */
    public abstract void invertSelect();

    /**
     * 是否允许取消已选状态
     * <p>
     * 当点击一个已选中的item时，是否可以将该item状态设为未选状态
     *
     * @param noCancel 默认false
     * @return
     */
    public void setNoCancel(boolean noCancel) {
        isNoCancel = noCancel;
    }



    /**
     * 警告：当选择数超过最大数，而且不自动取消旧的被选中的item时，会触发该警告
     */
    public interface Warning {
        void warn(String msg);
    }

    /**
     * 设置选中状态改变监听事件
     *
     * @param onSelectChange
     */
    public void setOnSelectChange(OnSelectChange<D> onSelectChange) {
        mOnSelectChange = onSelectChange;
    }

    /**
     * 选中状态改变监听
     *
     * @param <D>
     */
    public interface OnSelectChange<D> {
        /**
         * 选中状态改变
         *
         * @param position    发生改变的索引
         * @param isSelect    改变后的状态
         * @param d           被改变的数据
         */
        void onSelectChange(int position, boolean isSelect, D d);

        /**
         * 是否全选，全选和反选，还有全不选调用
         * @param isSelectAll
         */
        void onSelectAll( boolean isSelectAll);
    }
}
