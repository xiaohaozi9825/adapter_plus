package pw.xiaohaozi.adapter_plus.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.listener.OnItemLongClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnItemClickListener;
import pw.xiaohaozi.adapter_plus.listener.OnLongClickListener;


public class ViewHolder<VDB extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private VDB mBinding;
    OnItemClickListener<VDB> mOnItemClickListener;
    OnClickListener<VDB> mOnClickListener;

    OnItemLongClickListener<VDB> mOnItemLongClickListener;
    OnLongClickListener<VDB> mOnLongClickListener;
    private OnSelectChangeListener mOnSelectChangeListener;
    private View mTriggerView;
    //①将原来的形参 View itemView 改为 Binding
    public ViewHolder(@NonNull VDB vdb) {
        //②super中需要返回itemView，所以需要传入Binding.getRoot()
        super(vdb.getRoot());
        mBinding = vdb;
    }

    public VDB getBinding() {
        return mBinding;
    }



    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == mBinding.getRoot().getId()) {
            if (mOnItemLongClickListener != null)
                mOnItemLongClickListener.onItemLongClick(v, mBinding, getLayoutPosition());
        } else {
            if (mOnLongClickListener != null)
                mOnLongClickListener.onLongClick(v, mBinding, getLayoutPosition());
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener<VDB> itemClickListener) {
        mOnItemClickListener = itemClickListener;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener<VDB> onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<VDB> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
        mBinding.getRoot().setOnLongClickListener(this);
    }

    public void setOnLongClickListener(OnLongClickListener<VDB> onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    /**
     * 设置触发器
     *
     * @param view 指定由该view触发选中事件
     */
    public void setTrigger(View view) {
        if (mTriggerView != null) mTriggerView.setOnClickListener(null);
        mTriggerView = view;
        view.setOnClickListener(this);
    }

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        mOnSelectChangeListener = onSelectChangeListener;
    }

    @Override
    public void onClick(View v) {
        if (mTriggerView != null && v.getId() == mTriggerView.getId()) {
            Integer selectPosition = getLayoutPosition();
            mOnSelectChangeListener.change(this, selectPosition);
        } else {
            if (v.getId() == mBinding.getRoot().getId()) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, mBinding, getLayoutPosition());
            } else {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v, mBinding, getLayoutPosition());
                }
            }
        }
    }

    public interface OnSelectChangeListener {
        void change(ViewHolder selectHolder, Integer position);
    }
}
