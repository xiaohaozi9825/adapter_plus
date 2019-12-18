package pw.xiaohaozi.zkr.adapter;

import androidx.databinding.ViewDataBinding;
import pw.xiaohaozi.zkr.holder.ViewHolder;

/**
 * viewType 多类型
 * <p>
 * 当viewType为多类型itemView时，javaBean 类必须实现RecyclerData接口
 * 该类与父类BaseAdapter 唯一的不同是  D extends RecyclerData
 *
 * @param <D>
 */
public abstract class MultiTypeAdapter<D extends RecyclerData> extends BaseAdapter<ViewDataBinding, D, ViewHolder<ViewDataBinding>> {


}
