package pw.xiaohaozi.adapter_plus.adapter;

import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;


public abstract class SelectSimpleAdapter<VDB extends ViewDataBinding, D, VH extends SelectHolder<VDB>> extends SelectAdapter<VDB, D, VH> {
    private LinkedList<Integer> mSelectPosition = new LinkedList<>();


    /**
     * 增加一条选中的item
     *
     * @param d
     * @return
     */
    @Override
    public void addSelectItem(D d) {
        if (d == null) return;
        int position = getDataList().indexOf(d);
        if (position < 0) return;
        if (mSelectPosition.contains(position)) return;//如果已经是选中状态，不操作
        mSelectPosition.add(position);
        if (getDataList() != null && getDataList().size() > position) {
            notifyItemChanged(position);
            onSelectChange(position, true);
        }

    }

    /**
     * 取消选中状态
     *
     * @param d
     * @return
     */
    @Override
    public void cancelSelectItem(D d) {
        if (d == null) return;
        int position = getDataList().indexOf(d);
        if (position < 0) return;
        if (!mSelectPosition.contains(position)) return;//如果已经是未选中状态，不操作
        mSelectPosition.remove(position);
        if (getDataList() != null && getDataList().size() > position) {
            notifyItemChanged(position);
            onSelectChange(position, false);
        }
        return;
    }

    @Override
    public void invertSelect() {
        LinkedList<Integer> temp = (LinkedList<Integer>) mSelectPosition.clone();
        mSelectPosition.clear();
        for (int i = 0; i < getDataList().size(); i++)
            if (!temp.contains(i)) mSelectPosition.add(i);
    }


    /**
     * 全选
     *
     * @return
     */
    @Override
    public void selectAll() {
        mSelectPosition.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            mSelectPosition.add(i);
        }
        notifyDataSetChanged();
    }

    /**
     * 全不选
     *
     * @return
     */
    @Override
    public void cancelAll() {
        mSelectPosition.clear();
        notifyDataSetChanged();
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
    public <A extends List<D>> boolean refresh(A list) {
        mSelectPosition.clear();
        return super.refresh(list);
    }

    @Override
    protected <VG extends ViewGroup> VH onCreateViewHolder(@NonNull VG vg, VDB vdb, int viewType) {
        final VH vh = super.onCreateViewHolder(vg, vdb, viewType);
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
    protected void onBindViewHolder(@NonNull VH vh, int position,@NonNull  VDB vdb, @NonNull D d) {
        boolean isSelect = mSelectPosition.contains(position);
        onBindViewHolder(vh, position, vdb, d, isSelect);
    }


    /**
     * 当选中状态发生改变时会回调该方法
     *
     * @param position 被改变的索引
     * @param isSelect 是否被选中
     */
    protected void onSelectChange(int position, boolean isSelect) {
        if (mOnSelectChange != null) {
            mOnSelectChange.onSelectChange(position, isSelect, getDataList().get(position));
            mOnSelectChange.onSelectAll(mSelectPosition.size() == getDataList().size());
        }
    }

    /**
     * 绑定数据到view中
     * <p>
     * 该方法在选中状态改变时也会被调用
     *
     * @param vh
     * @param position
     * @param vdb
     * @param d
     * @param isSelect
     */
    protected abstract void onBindViewHolder(@NonNull VH vh, int position,@NonNull  VDB vdb, @NonNull D d, boolean isSelect);


}
