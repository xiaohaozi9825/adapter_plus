package pw.xiaohaozi.adapter_plus.data;

public interface ViewTyper {
    /**
     * 获取item类型，当recyclerview要显示多种item类型时，javaBean需要实现RecyclerData接口
     * 每个JavaBean中 get_RV_ItemViewType();方法必须返回不同的值
     * 不要嫌弃名字长，主要防止与javabean中字段冲突
     *
     * @return
     */
    int getItemViewType();
}
