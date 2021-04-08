package pw.xiaohaozi.adapter_plus.adapter;

import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

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
public abstract class SelectPlusAdapter<VDB extends ViewDataBinding, D, VH extends ViewHolder<VDB>>
        extends BaseAdapter<VDB, D, VH> {
    protected List<Check> mChecks;//已选列表 2020-7-22 15:38:29


    public SelectPlusAdapter(List<Check> checks) {
        if (checks == null) mChecks = new LinkedList<>();
        else mChecks = checks;
    }

    public SelectPlusAdapter() {
        mChecks = new LinkedList<>();
    }


    /**
     * 增加一条选中的item
     *
     * @param d
     * @return
     */
    @Override
    public void addSelectItem(D d) {
        if (d == null) return;
        if (!(d instanceof Check)) return;
        Check sd = (Check) d;
        if (sd.checkIndex() >= 0) return;//如果已经是选中状态，不操作
        sd.checkIndex(mChecks.size());
        mChecks.add(sd);
        if (getDataList() != null && getDataList().contains(d)) {
            int position = getDataList().indexOf(d);
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
        if (!(d instanceof Check)) return;
        Check sd = (Check) d;
        if (sd.checkIndex() < 0) return;//如果已经是未选中状态，不操作
        mChecks.remove(sd);
        sd.checkIndex(-1);
        refreshCheckIndex();
        if (getDataList() != null && getDataList().contains(d)) {
            int position = getDataList().indexOf(d);
            notifyItemChanged(position);
            onSelectChange(position, false);
        }
    }

    private void refreshCheckIndex() {
        int index = 0;
        for (Check check : mChecks) check.checkIndex(index++);
    }

    /**
     * 全选
     * 当限定了最大选择个数时，从第一个开始炫，直到达到可选的最大个数
     *
     * @return
     */
    @Override
    public void selectAll() {
        mChecks.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            D d = getDataList().get(i);
            if (!(d instanceof Check)) return;
            Check sd = (Check) d;
            if (mChecks.size() < mMaxSelectSize) {
                sd.checkIndex(mChecks.size());
                mChecks.add(sd);
            } else {
                sd.checkIndex(-1);
                refreshCheckIndex();
            }
        }
        notifyDataSetChanged();
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(true);

    }

    /**
     * 全不选
     *
     * @return
     */
    @Override
    public void cancelAll() {

        for (Check d : mChecks) {
            if (d == null) continue;
            d.checkIndex(-1);
        }
        mChecks.clear();
        notifyDataSetChanged();
        if (mOnSelectChange != null) mOnSelectChange.onSelectAll(false);

    }

    /**
     * 反选
     * <p>
     * 如果限定了最大可选个数，选中状态从第一个开始计数，当达到最大可选个数时，后面都不选
     *
     * @return
     */
    @Override
    public void invertSelect() {
        mChecks.clear();
        for (int i = 0; i < getDataList().size(); i++) {
            D d = getDataList().get(i);
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
        }
        if (mOnSelectChange != null)
            mOnSelectChange.onSelectAll(mChecks.size() == getDataList().size());

        notifyDataSetChanged();
    }

    /**
     * 获取被选中的item
     *
     * @return
     */
    public List<Check> getChecks() {
        return mChecks;
    }

    @Override
    public <A extends List<D>> boolean refresh(A list) {
        return super.refresh(list);
    }

    @Override
    protected <VG extends ViewGroup> VH onCreateViewHolder(@NonNull VG vg, @NonNull VDB vdb, int viewType) {
        final VH vh = createViewHolder(vg, vdb, viewType);
        vh.setOnSelectChangeListener((ViewHolder, position) -> {
            D d = getDataList().get(position);
            if (!(d instanceof Check)) return;
            Check sd = (Check) d;
            //先判断该item是否已经被选中了，如果是，则取消选择
            if (sd.checkIndex() >= 0) {
                if (isNoCancel) return;//如果禁止取消，则不执行任何操作
                mChecks.remove(d);
                sd.checkIndex(-1);
                notifyItemChanged(position);
                onSelectChange(position, false);
                return;
            }
            //如果选择个数最大可选个数，则移除选中的第一个
            if (mMaxSelectSize <= mChecks.size()) {
                if (isAutoRemove) {
                    Check first = mChecks.remove(mChecks.size() - 1);

                    first.checkIndex(-1);
                    int indexOf = getDataList().indexOf(first);
                    notifyItemChanged(indexOf);
                    onSelectChange(indexOf, false);
                } else {
                    if (mAutoRemoveWarning != null)
                        mAutoRemoveWarning.warn("您最多只能选中" + mMaxSelectSize + "条");
                    return;
                }
            }
            sd.checkIndex(mChecks.size());
            mChecks.add(sd);
            notifyItemChanged(position);
            onSelectChange(position, true);
        });
        return vh;
    }

    protected abstract <VG extends ViewGroup> VH createViewHolder(@NonNull VG vg, @NonNull VDB vdb, int viewType);

    @Override
    protected void onBindViewHolder(@NonNull VH vh, int position, @NonNull VDB vdb, @NonNull D d) {
        D d1 = getDataList().get(position);
        if (d1 == null) {
            onBindViewHolder(vh, position, vdb, d, -1);
        } else {
            if ((d instanceof Check)) {
                Check sd = (Check) d;
                onBindViewHolder(vh, position, vdb, d, sd.checkIndex());
            }else {
                onBindViewHolder(vh, position, vdb, d, -1);
            }
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
    protected abstract void onBindViewHolder(@NonNull VH vh, int position, @NonNull VDB vdb, @NonNull D d, int isSelect);

    /**
     * 当选中状态发生改变时会回调该方法
     *
     * @param position 被改变的索引
     * @param isSelect 是否被选中
     */
    protected void onSelectChange(int position, boolean isSelect) {
        if (mOnSelectChange != null) {
            mOnSelectChange.onSelectChange(position, isSelect, getDataList().get(position));
            mOnSelectChange.onSelectAll(mChecks.size() == getDataList().size());
        }
    }

}
