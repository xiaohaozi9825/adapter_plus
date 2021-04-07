package pw.xiaohaozi.adapter_plus.adapter;

import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.adapter_plus.holder.SelectHolder;

/**
 * 描述：
 * 作者：小耗子
 * 简书地址：https://www.jianshu.com/u/2a2ea7b43087
 * github：https://github.com/xiaohaozi9825
 * 创建时间：2020/7/22 0022 10:31
 */
public abstract class SelectAdapter<VDB extends ViewDataBinding, D, VH extends SelectHolder<VDB>>
        extends SingleTypeAdapter<VDB, D, VH> {
    OnSelectChange<D> mOnSelectChange;
    int mMaxSelectSize = Integer.MAX_VALUE;//最多可以选中几个
    Warning mAutoRemoveWarning;
    boolean isAutoRemove = true;//当超出选中个数后是否自动删除最先选中的
    boolean isNoCancel = false;//是否禁止取消,当再次点击被选中的目标是，不执行任何操作

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
    public void setAutoRemove(boolean autoRemove, SelectAdapter.Warning warning) {
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
