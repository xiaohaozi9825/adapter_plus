package pw.xiaohaozi.adapter_plus.adapter;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;


public abstract class SelectAdapter<VDB extends ViewDataBinding, D, VH extends SelectHolder<VDB>> extends SingleTypeAdapter<VDB, D, VH> {
    private LinkedList<Integer> mSelectPosition = new LinkedList<>();
    private int mMaxSelectSize = Integer.MAX_VALUE;//最多可以选中几个
    private boolean isAutoRemove = true;//当超出选中个数后是否自动删除最先选中的
    private boolean isNoCancel = false;//是否禁止取消,当再次点击被选中的目标是，不执行任何操作
    private Warning mAutoRemoveWarning;

    /**
     * 最多可以选择多少项
     * <p>
     * 默认Integer.MAX_VALUE
     * 如果想单选，则传入 1
     *
     * @param maxSelectSize
     * @return
     */
    public SelectAdapter setMaxSelectSize(int maxSelectSize) {
        mMaxSelectSize = maxSelectSize;
        return this;
    }

    /**
     * 当选择个数大于 mMaxSelectSize 时，是否自动取消第一个选中的item
     *
     * @param autoRemove 默认true
     * @param warning    只有autoRemove==false 才能出发，当选中个数超过mMaxSelectSize时，
     *                   会回调warning.warn()方法。
     * @return
     */
    public SelectAdapter setAutoRemove(boolean autoRemove, Warning warning) {
        isAutoRemove = autoRemove;
        mAutoRemoveWarning = warning;
        return this;
    }

    /**
     * 增加一条选中的item
     *
     * @param position
     * @return
     */
    public SelectAdapter addSelectItem(Integer position) {
        if (mSelectPosition.contains(position)) return this;//如果已经是选中状态，不操作
        mSelectPosition.add(position);
        if (getDataList() != null && getDataList().size() > position) {
            notifyItemChanged(position);
            onSelectChange(position, true);
        }
        return this;
    }

    /**
     * 取消选中状态
     *
     * @param position
     * @return
     */
    public SelectAdapter cancelSelectItem(Integer position) {
        if (!mSelectPosition.contains(position)) return this;//如果已经是未选中状态，不操作
        mSelectPosition.remove(position);
        if (getDataList() != null && getDataList().size() > position) {
            notifyItemChanged(position);
            onSelectChange(position, false);
        }
        return this;
    }

    /**
     * 全选
     *
     * @return
     */
    public SelectAdapter selectAll() {
        mSelectPosition.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            mSelectPosition.add(i);
        }
        notifyDataSetChanged();
        return this;
    }

    /**
     * 全不选
     *
     * @return
     */
    public SelectAdapter cancelAll() {
        mSelectPosition.clear();
        notifyDataSetChanged();
        return this;
    }

    /**
     * 是否允许取消已选状态
     * <p>
     * 当点击一个已选中的item时，是否可以将该item状态设为未选状态
     *
     * @param noCancel 默认false
     * @return
     */
    public SelectAdapter setNoCancel(boolean noCancel) {
        isNoCancel = noCancel;
        return this;
    }

    /**
     * 获取被选中的item索引
     *
     * @return
     */
    public LinkedList<Integer> getSelectPosition() {
        return mSelectPosition;
    }

    @Override
    public void refresh(ObservableList<D> list) {
        mSelectPosition.clear();
        super.refresh(list);
    }

    @Override
    protected VH onCreateViewHolder(@NonNull RecyclerView recyclerView, VDB binding, int viewType) {
        final VH vh = super.onCreateViewHolder(recyclerView,binding, viewType);
        vh.setOnSelectChangeListener((selectHolder, position) -> {
            //先判断该item是否已经被选中了，如果是，则取消选择
            if (mSelectPosition.contains(position)) {
                if (isNoCancel) return;//如果禁止取消，则不执行任何操作
                mSelectPosition.remove(position);
                notifyItemChanged(position);
                onSelectChange(position, false);
                return;
            }
            if (mMaxSelectSize <= mSelectPosition.size()) {
                if (isAutoRemove) {
                    Integer first = mSelectPosition.removeFirst();
                    notifyItemChanged(first);
                    onSelectChange(first, false);
                } else {
                    if (mAutoRemoveWarning != null)
                        mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
                    return;
                }
            }
            mSelectPosition.add(position);
            notifyItemChanged(position);
            onSelectChange(position, true);
        });
//        vh.setOnItemClickListener(vdb -> {
//            Integer selectPosition = vh.getLayoutPosition();
//            //先判断该item是否已经被选中了，如果是，则取消选择
//            if (mSelectPosition.contains(selectPosition)) {
//                if (isNoCancel) return;//如果禁止取消，则不执行任何操作
//                mSelectPosition.remove(selectPosition);
//                notifyItemChanged(selectPosition);
//                onSelectChange(selectPosition, false);
//                return;
//            }
//            if (mMaxSelectSize <= mSelectPosition.size()) {
//                if (isAutoRemove) {
//                    Integer first = mSelectPosition.removeFirst();
//                    notifyItemChanged(first);
//                    onSelectChange(first, false);
//                } else {
//                    if (mAutoRemoveWarning != null)
//                        mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
//                    return;
//                }
//            }
//            mSelectPosition.add(selectPosition);
//            notifyItemChanged(selectPosition);
//            onSelectChange(selectPosition, true);
//        });
        return vh;
    }

    @Override
    final protected void onBindViewHolder(int position, VH viewHolder) {
        boolean isSelect = mSelectPosition.contains(position);
        onBindViewHolder(viewHolder, position, isSelect);
    }

    /**
     * 当选中状态发生改变时会回调该方法
     *
     * @param position 被改变的索引
     * @param isSelect 是否被选中
     */
    protected abstract void onSelectChange(int position, boolean isSelect);

    /**
     * 绑定数据到view中
     * <p>
     * 该方法在选中状态改变时也会被调用
     *
     * @param viewHolder
     * @param position
     * @param isSelect
     */
    protected abstract void onBindViewHolder(VH viewHolder, int position, boolean isSelect);

    /**
     * 警告：当选择数超过最大数，而且不自动取消旧的被选中的item时，会触发该警告
     */
    public interface Warning {
        void warn(String msg);
    }
}
