package com.iflytek.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.myapplication.adapter.FriendAdapter;
import com.iflytek.myapplication.bean.FriendInfo;

public class SimpleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FriendAdapter mFriendAdapter;
    private ObservableArrayList<FriendInfo> mFriendInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false));

        mFriendAdapter = new FriendAdapter();
        mFriendAdapter.setOnItemClickListener(binding ->
                Toast.makeText(this, "您点击了：" + binding.getFriendInfo().getName(),
                        Toast.LENGTH_SHORT).show()
        );
        mFriendAdapter.setOnItemLongClickListener(binding ->
                Toast.makeText(this, "您长按了：" + binding.getFriendInfo().getName(),
                        Toast.LENGTH_SHORT).show());

        mRecyclerView.setAdapter(mFriendAdapter);
    }

    private void initData() {
        //这里需要模拟一些数据
        mFriendInfos = new ObservableArrayList<>();

        mFriendInfos.add(new FriendInfo("马化腾", "data:image/jpeg;base64,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", "腾讯应该少提“产品”和“功能”，多谈“服务”和“特性”"));
        mFriendInfos.add(new FriendInfo("雷军", "data:image/jpeg;base64,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", "小米的成功可以复制是外界误读"));
        mFriendInfos.add(new FriendInfo("李彦宏", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4022122344,1263781155&fm=26&gp=0.jpg", "不拥抱互联网和新技术，可能会被淘汰掉"));
        mFriendInfos.add(new FriendInfo("黄章", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2676436010,3530283354&fm=26&gp=0.jpg", "和老罗小米比炒作，我肯定输。要是比做产品，我可以秒他们几条街"));
        mFriendInfos.add(new FriendInfo("刘强东", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=613261035,3821504487&fm=26&gp=0.jpg", "我脸盲，不知道她漂不漂亮"));
        mFriendInfos.add(new FriendInfo("王健林", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322891556&di=662826fbc3a87917c769246e25fc2711&imgtype=0&src=http%3A%2F%2Fimage.thepaper.cn%2Fwap%2Fimage%2F4%2F375%2F847.jpg", "但是最好先定一个能达到的小目标。比如我先挣它一个亿。"));
        mFriendInfos.add(new FriendInfo("马云", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576322838876&di=4352cdbb664326b23ec43d538e019a79&imgtype=0&src=http%3A%2F%2Ffile.qiyejia.info%2Fspider%2F2017%2F01%2F07%2F2017010710145432995.jpg", "我对钱没兴趣"));

        mFriendAdapter.refresh(mFriendInfos);
        mFriendAdapter.add(mFriendInfos);
        mFriendAdapter.add(mFriendInfos);
    }


}