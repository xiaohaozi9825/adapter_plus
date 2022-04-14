package pw.xiaohaozi.adapter_plus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.data.Check;
import pw.xiaohaozi.adapter_plus.data.ViewTyper;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;
import pw.xiaohaozi.adapter_plus.listener.OnClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnItemClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnItemLongClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnLongClickListener;

/**
 * 该类对RecyclerView.Adapter做了进一步封装，使其使用更加简单方便
 *
 * @param <VDB>
 * @param <D>
 * @param <VH>
 */
public abstract class BaseAdapter<VDB extends ViewDataBinding, D, VH extends ViewHolder<VDB>> extends RecyclerView.Adapter<VH> {
    private List<D> mDatas;
    private DynamicChangeCallback<?> mDynamicChangeCallback;
    protected Context mContext;
    private OnItemClickListener<VDB> mOnItemClickListener;
    private OnClickListener<VDB> mOnClickListener;
    private OnItemLongClickListener<VDB> mOnItemLongClickListener;
    private OnLongClickListener<VDB> mOnLongClickListener;

    OnSelectChange<D> mOnSelectChange;
    int mMaxSelectSize = Integer.MAX_VALUE;//最多可以选中几个
    Warning mAutoRemoveWarning;
    boolean isAutoRemove = true;//当超出选中个数后是否自动删除最先选中的
    boolean isNoCancel = false;//是否禁止取消,当再次点击被选中的目标是，不执行任何操作
    protected List<Check> mChecks = new LinkedList<>();//已选列表 2020-7-22 15:38:29

    /**
     * 增加一个数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param data
     */
    @Deprecated
    public boolean add(D data) {
        if (mDatas == null) {
            mDatas = new ObservableArrayList<>();
            mDatas.add(data);
            return refresh(mDatas);
        } else {
//            if (!mDataList.contains(data)) {
//                mDataList.add(data);
//            }

            if (mDatas instanceof ObservableList)
                return mDatas.add(data);
            else {
                boolean add = mDatas.add(data);
                if (add) notifyItemInserted(mDatas.size() - 1);
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
        if (mDatas == null) {
            mDatas = new ObservableArrayList<>();
            mDatas.add(data);
            return refresh(mDatas);
        } else {
            mDatas.add(position, data);
            if (!(mDatas instanceof ObservableList)) notifyItemInserted(position);
            return true;
        }
    }

    /**
     * 追加数据，可用于上拉加载更多
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     *
     * @param list
     */
    @Deprecated
    public <L extends List<? extends D>> boolean add(L list) {
        if (mDatas == null) {
            return refresh(list);
        } else {
            boolean b = mDatas.addAll(list);
            notifyItemRangeInserted(mDatas.size() - list.size(), list.size());
            return b;
        }
    }

    @Deprecated
    public <L extends List<? extends D>> boolean add(int position, L list) {
        if (mDatas == null) {
            return refresh(list);
        } else {
            boolean b;
            if (position > mDatas.size()) {
                b = mDatas.addAll(list);
                notifyItemRangeInserted(mDatas.size() - list.size(), list.size());
            } else if (position < 0) {
                b = mDatas.addAll(0, list);
                notifyItemRangeInserted(0, list.size());
            } else {
                b = mDatas.addAll(position, list);
                notifyItemRangeInserted(position, list.size());
            }
            return b;
        }
    }


    /**
     * 更新所有item
     * 使用notifyDataSetChanged()方法刷新列表，会将所有数据重新绑定一次，这样会出现闪烁。
     * 如果不需要重新绑定数据，可以使用该方法，但是可能存在性能问题
     */
    final public void notifyAllItemChanged() {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            notifyItemChanged(i);
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
        if (mDatas == null) return false;
        D data = mDatas.remove(position);
        if (!(mDatas instanceof ObservableList)) notifyItemRemoved(position);
        if (data instanceof Check) {
            int checkIndex = mChecks.indexOf(data);
            mChecks.remove(data);
            refreshCheckIndex(checkIndex);
        }
        return data != null;
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
        if (mDatas == null) return false;
        if (data == null) return false;
        int indexOf = mDatas.indexOf(data);
        if (indexOf >= 0) {
            boolean remove = mDatas.remove(data);
            if (!(mDatas instanceof ObservableList)) notifyItemRemoved(indexOf);
            if (data instanceof Check) {
                int checkIndex = mChecks.indexOf(data);
                mChecks.remove(data);
                refreshCheckIndex(checkIndex);
            }
            return remove;
        }
        return false;
    }

    /**
     * 移除所有数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     */
    @Deprecated
    public boolean remove() {
        if (mDatas == null) return false;
        mDatas.clear();
        mChecks.clear();
        if (!(mDatas instanceof ObservableList)) notifyDataSetChanged();
        return true;
    }

    /**
     * 移除所有数据
     * <p>
     * 弃用原因：如果数据 D 继承了BaseObservable,则可以实时更新数据了，无需调用该方法
     */
    @Deprecated
    public <L extends List<? extends D>> boolean remove(L list) {
        if (mDatas == null) return false;
        if (list == null) return false;
        ArrayList<Integer> indexList = new ArrayList<>();
        int checkIndex = Integer.MAX_VALUE;
        for (D d : list) {
            indexList.add(mDatas.indexOf(d));
            if (d instanceof Check) checkIndex = Math.min(checkIndex, mChecks.indexOf(d));
        }
        boolean remove = mDatas.removeAll(list);

        mChecks.removeAll(list);
        int index = 0;
        for (Check check : mChecks) {
            check.checkIndex(index++);
        }
        notifyDataSetChanged();
        return remove;
    }

    /**
     * 刷新数据，可用于第一次加载数据或下拉刷新
     *
     * @param list
     */
    public <L extends List<? extends D>> boolean refresh(L list) {
        if (list == null) return false;
        mChecks.clear();
        if (mDatas instanceof ObservableList && mDynamicChangeCallback != null)
            ((ObservableList) mDatas).removeOnListChangedCallback(mDynamicChangeCallback);

        mDatas = (List<D>) list;
        if (list instanceof ObservableList) {
            if (mDynamicChangeCallback == null)
                mDynamicChangeCallback = new DynamicChangeCallback<>(this);
            ((ObservableList) mDatas).addOnListChangedCallback(mDynamicChangeCallback);
        } else {
            notifyDataSetChanged();
        }
        return true;
    }

    public boolean remove(int start, int count) {
        if (start < 0) return false;
        if (count + start > mDatas.size()) return false;
        for (int i = 0; i < count; i++) {
            mDatas.remove(start);
        }
        notifyItemRangeRemoved(start, count);
        return true;
    }

    /**
     * 销毁，activity或fragment销毁时调用
     * 1、清除对数据的监听
     */
    public void destroy() {
        if (mDatas instanceof ObservableList && mDynamicChangeCallback != null)
            ((ObservableList) mDatas).removeOnListChangedCallback(mDynamicChangeCallback);
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

    public List<? extends D> getDatas() {
        return mDatas;
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
        vh.setOnSelectChangeListener((ViewHolder, position) -> {
            if (position < 0 || position >= getDatas().size()) return;
            D d = getDatas().get(position);
            if (!(d instanceof Check)) return;
            Check check = (Check) d;
            //先判断该item是否已经被选中了，如果是，则取消选择
            int checkIndex = check.checkIndex();
            if (checkIndex >= 0) {
                if (isNoCancel) return;//如果禁止取消，则不执行任何操作
                mChecks.remove(check);
                check.checkIndex(-1);

                notifyItemChanged(position);
                refreshCheckIndex(checkIndex);

                onSelectChange(position, false, true);
                return;
            }
            //如果选择个数最大可选个数，则移除选中的第一个
            if (mMaxSelectSize <= mChecks.size()) {
                if (isAutoRemove) {
                    Check first = mChecks.remove(0);
                    first.checkIndex(-1);

                    int indexOf = getDatas().indexOf(first);
                    if (indexOf < 0 || indexOf >= mDatas.size()) return;
                    notifyItemChanged(indexOf);

                    refreshCheckIndex(0);
                    onSelectChange(indexOf, false, false);
                } else {
                    if (mAutoRemoveWarning != null)
                        mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
                    return;
                }
            }
            check.checkIndex(mChecks.size());
            mChecks.add(check);
            notifyItemChanged(position);
            onSelectChange(position, true, true);
        });

        return vh;

    }


    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {
        D d = getDatas().get(position);
        if (d == null) {
            onBindViewHolder(viewHolder, position, viewHolder.getBinding(), d, -1);
        } else {
            if ((d instanceof Check)) {
                Check sd = (Check) d;
                onBindViewHolder(viewHolder, position, viewHolder.getBinding(), d, sd.checkIndex());
            } else {
                onBindViewHolder(viewHolder, position, viewHolder.getBinding(), d, -1);
            }
        }
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


    protected abstract int getLayoutRes(int viewType);

    @Override
    final public int getItemViewType(int position) {
        D d = mDatas.get(position);
        if (d instanceof ViewTyper) {
            return ((ViewTyper) d).getItemViewType();
        } else {
            return super.getItemViewType(position);
        }
    }

    /**
     * 绑定数据到view中
     * <p>
     * 该方法在选中状态改变时也会被调用
     *
     * @param holder
     * @param position
     * @param binding
     * @param data
     * @param checkIndex
     */
    protected abstract void onBindViewHolder(@NonNull VH holder, int position, @NonNull VDB binding, @Nullable D data, int checkIndex);

    @Override
    final public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /**
     * 动态更新列表
     * 当ObservableList数据变化时动态更新ui
     *
     * @param <T>
     */
    final private class DynamicChangeCallback<T extends BaseAdapter> extends
            ObservableList.OnListChangedCallback<ObservableList<T>> {

        private RecyclerView.Adapter<VH> adapter;

        public DynamicChangeCallback(RecyclerView.Adapter<VH> adapter) {
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
         * @param position 发生改变的索引
         * @param isSelect 改变后的状态
         * @param d        被改变的数据
         * @param isClick  该状态的改变是否是由于点击事件触发的
         *                 1. 点击触发 true
         *                 2. 被选中数>最大可选择数时，被自动取消选中 false
         *                 3. 调用addSelect()方法 false
         *                 4. 调用selectAll()方法 false
         *                 5. 调用cancelAll()方法 false
         */
        void onSelectChange(int position, boolean isSelect, D d, boolean isClick);

        /**
         * 是否全选，全选和反选，还有全不选调用
         *
         * @param isSelectAll
         */
        void onSelectAll(boolean isSelectAll);
    }


    /**
     * 增加一条选中的item
     *
     * @param d
     * @return
     */
    public void addSelectItem(D d) {
        if (d == null) return;
        if (!(d instanceof Check)) return;
        Check check = (Check) d;
        if (check.checkIndex() >= 0) return;//如果已经是选中状态，不操作
        if (mChecks.size() >= mMaxSelectSize) {//如果已经到达最大个数了
            if (!isAutoRemove) {
                if (mAutoRemoveWarning != null)
                    mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
                return;
            }
        }
        mChecks.add(check);//添加到数组中
        //判断是否超过最大选择数
        if (mChecks.size() > mMaxSelectSize) {//如果超过
            Check remove = mChecks.remove(0);//① 删除最前面添加的item
            remove.checkIndex(-1);//②将删除后的item设为未选择状态
            notify(remove, it -> onSelectChange(it, false, false));//③刷新item
            //遍历所有选中的item，修改索引，并刷新
            for (int i = 0; i < mChecks.size(); i++) {
                Check check1 = mChecks.get(i);
                check1.checkIndex(i);
                notify(check1, it ->
                        {
                            if (check == check1) onSelectChange(it, true, false);
                        }
                );
            }
        } else {//如果没有超过最大选择数，则只刷新最后添加进来的item
            check.checkIndex(mChecks.size() - 1);//设置选中索引
            notify(check, it -> onSelectChange(it, true, false)
            );
        }
    }

    private void notify(Check check, Consumer<Integer> consumer) {
        if (getDatas() != null && getDatas().contains(check)) {
            int position = mDatas.indexOf(check);
            if (position < 0 || position >= mDatas.size()) return;
            notifyItemChanged(position);
            consumer.accept(position);
        }
    }

    /**
     * 取消选中状态
     *
     * @param d
     * @return
     */

    public void cancelSelectItem(D d) {
        if (d == null) return;
        if (!(d instanceof Check)) return;
        Check check = (Check) d;
        int checkIndex = check.checkIndex();
        if (checkIndex < 0) return;//如果已经是未选中状态，不操作
        mChecks.remove(check);
        check.checkIndex(-1);
        if (mDatas == null) return;
        refreshCheckIndex(checkIndex);
        if (mDatas.contains(d)) {
            int position = getDatas().indexOf(d);
            if (position < 0 || position >= mDatas.size()) return;
            notifyItemChanged(position);
            onSelectChange(position, false, false);
        }
    }

    private void refreshCheckIndex(int startIndex) {
        int index = 0;
        for (Check check : mChecks) {
            check.checkIndex(index++);
            int indexOf = mDatas.indexOf(check);
            if (indexOf >= 0 && index > startIndex) notifyItemChanged(indexOf);
        }
    }

    /**
     * 全选
     * 当限定了最大选择个数时，从第一个开始炫，直到达到可选的最大个数
     *
     * @return
     */
    public void selectAll() {
        mChecks.clear();
        for (int i = 0; i < getDatas().size(); i++) {
            D d = getDatas().get(i);
            if (!(d instanceof Check)) return;
            Check sd = (Check) d;
            if (mChecks.size() < mMaxSelectSize) {
                sd.checkIndex(mChecks.size());
                mChecks.add(sd);
            } else {
                sd.checkIndex(-1);
//                refreshCheckIndex();
            }
            notifyItemChanged(i);
            if (mOnSelectChange != null)
                mOnSelectChange.onSelectChange(i, true, d, false);
        }
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(true);

    }

    /**
     * 全不选
     *
     * @return
     */
    public void cancelAll() {
        for (int i = 0; i < getDatas().size(); i++) {
            D d = getDatas().get(i);
            if (d == null) continue;
            Check check = (Check) d;
            check.checkIndex(-1);
            notifyItemChanged(i);
            if (mOnSelectChange != null)
                mOnSelectChange.onSelectChange(i, false, d, false);
        }
        mChecks.clear();
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(false);
    }

    /**
     * 反选
     * <p>
     * 如果限定了最大可选个数，选中状态从第一个开始计数，当达到最大可选个数时，后面都不选
     *
     * @return
     */
    public void invertSelect() {
        mChecks.clear();
        for (int i = 0; i < getDatas().size(); i++) {
            D d = getDatas().get(i);
            if (!(d instanceof Check)) return;
            Check sd = (Check) d;
            int checkIndex = sd.checkIndex();
            if (mChecks.size() < mMaxSelectSize) {
                if (checkIndex < 0) {
                    sd.checkIndex(mChecks.size());
                    mChecks.add(sd);
                } else {
                    sd.checkIndex(-1);
                }
            } else {
                sd.checkIndex(-1);
            }
            notifyItemChanged(i);
            if (mOnSelectChange != null)
                mOnSelectChange.onSelectChange(i, sd.checkIndex() >= 0, d, false);
        }
//        refreshCheckIndex();
        if (mOnSelectChange != null)
            mOnSelectChange.onSelectAll(mChecks.size() == getDatas().size());
    }

    /**
     * 获取被选中的item
     *
     * @return
     */
    public List<Check> getChecks() {
        return mChecks;
    }

    public void setChecks(List<? extends Check> checks) {
        mChecks.clear();
        mChecks.addAll(checks);
    }

    /**
     * 当选中状态发生改变时会回调该方法
     *
     * @param position 被改变的索引
     * @param isSelect 是否被选中
     * @param isClick  是否是被点击事件触发的状态改变
     */
    protected void onSelectChange(int position, boolean isSelect, boolean isClick) {
        if (mOnSelectChange != null) {
            mOnSelectChange.onSelectChange(position, isSelect, getDatas().get(position), isClick);
            mOnSelectChange.onSelectAll(mChecks.size() == getDatas().size());
        }
    }


}
