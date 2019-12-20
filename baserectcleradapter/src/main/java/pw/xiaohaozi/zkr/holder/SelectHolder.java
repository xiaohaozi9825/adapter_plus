package pw.xiaohaozi.zkr.holder;

import android.view.View;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class SelectHolder<VDB extends ViewDataBinding> extends ViewHolder<VDB> {
    private OnSelectChangeListener mOnSelectChangeListener;
    private View mTriggerView;

    public SelectHolder(@NonNull VDB binding) {
        super(binding);
        setTrigger(binding.getRoot());
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
            super.onClick(v);
        }
    }

    public interface OnSelectChangeListener {
        void change(SelectHolder selectHolder, Integer position);
    }
}
