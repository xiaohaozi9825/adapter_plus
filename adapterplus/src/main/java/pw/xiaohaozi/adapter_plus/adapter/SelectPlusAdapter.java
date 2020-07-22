package pw.xiaohaozi.adapter_plus.adapter;

import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;

/**
 * 描述：选择器适配器升级版，在数据结构中增加是否选择的状态值，用于处理更加复杂的选择情况
 * SelectSimpleAdapter 记录的是被选中的索引，数据刷新后，之前选中状态不再记录
 * SelectPlusAdapter 选中状态记录在数据中，SelectPlusAdapter不再记录被选中的索引。
 * 使用选择：
 * 1、如果简单的选择器，使用SelectAdapter即可，性能相对要比SelectPlusAdapter高。
 * <p>
 * 2、如果多个RecyclerView共用一组数据，而且要保持在不同RecyclerView中的选中状态，
 * 使用SelectPlusAdapter（如带搜索的好友选择器）。
 * <p>
 * 3、同一个RecyclerView切换不同的数据，当切换回来后还需要保持上次的选中状态（如本地图片选择器）
 * <p>
 * 作者：小耗子
 * 简书地址：https://www.jianshu.com/u/2a2ea7b43087
 * github：https://github.com/xiaohaozi9825
 * 创建时间：2020/7/21 0021
 */
public abstract class SelectPlusAdapter<VDB extends ViewDataBinding, D extends SelectData, VH extends SelectHolder<VDB>>
        extends SelectAdapter<VDB, D, VH> {
    protected List<D> mSelecteds;//已选列表 2020-7-22 15:38:29


    public SelectPlusAdapter(List<D> selecteds) {
        if (selecteds == null) mSelecteds = new LinkedList<>();
        else mSelecteds = selecteds;
    }


    /**
     * 增加一条选中的item
     *
     * @param d
     * @return
     */
    @Override
    public <S extends SelectAdapter> S addSelectItem(D d) {
        if (d == null) return (S) this;
        if (d.isSelected___()) return (S) this;//如果已经是选中状态，不操作

        mSelecteds.add(d);
        d.setSelected___(true);
        if (getDataList() != null && getDataList().contains(d)) {
            int position = getDataList().indexOf(d);
            notifyItemChanged(position);
            onSelectChange(position, true);
        }
        return (S) this;
    }

    /**
     * 取消选中状态
     *
     * @param d
     * @return
     */

    @Override
    public <S extends SelectAdapter> S cancelSelectItem(D d) {
        if (d == null) return (S) this;
        if (!d.isSelected___()) return (S) this;//如果已经是未选中状态，不操作
        mSelecteds.remove(d);
        d.setSelected___(false);
        if (getDataList() != null && getDataList().contains(d)) {
            int position = getDataList().indexOf(d);
            notifyItemChanged(position);
            onSelectChange(position, false);
        }
        return (S) this;
    }

    /**
     * 全选
     * 当限定了最大选择个数时，从第一个开始炫，直到达到可选的最大个数
     *
     * @return
     */
    @Override
    public <S extends SelectAdapter> S selectAll() {
        mSelecteds.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            D d = getDataList().get(i);
            if (mSelecteds.size() < mMaxSelectSize) {
                mSelecteds.add(d);
                d.setSelected___(true);
            } else {
                d.setSelected___(false);
            }
        }
        notifyDataSetChanged();
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(true);
        return (S) this;
    }

    /**
     * 全不选
     *
     * @return
     */
    @Override
    public <S extends SelectAdapter> S cancelAll() {
        for (D d : mSelecteds) d.setSelected___(false);
        mSelecteds.clear();
        notifyDataSetChanged();
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(false);

        return (S) this;
    }

    /**
     * 反选
     * <p>
     * 如果限定了最大可选个数，选中状态从第一个开始计数，当达到最大可选个数时，后面都不选
     *
     * @return
     */
    @Override
    public <S extends SelectAdapter> S invertSelect() {
        mSelecteds.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            D d = getDataList().get(i);
            boolean selected___ = d.isSelected___();
            if (mSelecteds.size() < mMaxSelectSize) {
                if (!selected___) mSelecteds.add(d);
                d.setSelected___(!selected___);
            } else {
                d.setSelected___(false);
            }
        }
        if (mOnSelectChange != null)
            mOnSelectChange.onSelectAll(mSelecteds.size() == getDataList().size());

        notifyDataSetChanged();
        return (S) this;
    }

    /**
     * 获取被选中的item
     *
     * @return
     */
    public List<D> getSelecteds() {
        return mSelecteds;
    }

    @Override
    public boolean refresh(List<D> list) {
        return super.refresh(list);
    }

    @Override
    protected <VG extends ViewGroup> VH onCreateViewHolder(@NonNull VG vg, VDB vdb, int viewType) {
        final VH vh = super.onCreateViewHolder(vg, vdb, viewType);
        vh.setOnSelectChangeListener((selectHolder, position) -> {
            D d = getDataList().get(position);
            //先判断该item是否已经被选中了，如果是，则取消选择
            if (d.isSelected___()) {
                if (isNoCancel) return;//如果禁止取消，则不执行任何操作
                mSelecteds.remove(d);
                d.setSelected___(false);
                notifyItemChanged(position);
                onSelectChange(position, false);
                return;
            }
            //如果选择个数最大可选个数，则移除选中的第一个
            if (mMaxSelectSize <= mSelecteds.size()) {
                if (isAutoRemove) {
                    D first = mSelecteds.remove(mSelecteds.size() - 1);
                    first.setSelected___(false);
                    int indexOf = getDataList().indexOf(first);
                    notifyItemChanged(indexOf);
                    onSelectChange(indexOf, false);
                } else {
                    if (mAutoRemoveWarning != null)
                        mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
                    return;
                }
            }
            mSelecteds.add(d);
            d.setSelected___(true);
            notifyItemChanged(position);
            onSelectChange(position, true);
        });
        return vh;
    }

    @Override
    protected void onBindViewHolder(VH vh, int position, VDB vdb, D d) {
        D d1 = getDataList().get(position);
        if (d1 == null) {
            onBindViewHolder(vh, position, vdb, d, false);
        } else {
            onBindViewHolder(vh, position, vdb, d, d1.isSelected___());
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
    protected abstract void onBindViewHolder(VH vh, int position, VDB vdb, D d, boolean isSelect);

    /**
     * 当选中状态发生改变时会回调该方法
     *
     * @param position 被改变的索引
     * @param isSelect 是否被选中
     */
    protected void onSelectChange(int position, boolean isSelect) {
        if (mOnSelectChange != null) {
            mOnSelectChange.onSelectChange(position, isSelect, getDataList().get(position));
            mOnSelectChange.onSelectAll(mSelecteds.size() == getDataList().size());
        }
    }

}
