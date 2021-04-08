package pw.xiaohaozi.adapter_plus.adapter;

public interface Check {
    /**
     * 选中的索引，如果未被选中，则返回-1
     *
     * @return
     */
    int checkIndex();

    /**
     * 被选中后的索引，-1表示取消选中
     * @param check
     */
    void checkIndex(int check);
}
