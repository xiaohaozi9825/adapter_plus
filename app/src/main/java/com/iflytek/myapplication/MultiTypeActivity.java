package com.iflytek.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pw.xiaohaozi.adapter_plus.adapter.RecyclerData;

import android.os.Bundle;

import com.iflytek.myapplication.adapter.ShoppingTrolleyAdapter;
import com.iflytek.myapplication.bean.GoodsBottomInfo;
import com.iflytek.myapplication.bean.GoodsCentreInfo;
import com.iflytek.myapplication.bean.GoodsTopInfo;

public class MultiTypeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ShoppingTrolleyAdapter mShoppingTrolleyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    mShoppingTrolleyAdapter = new ShoppingTrolleyAdapter();

    mRecyclerView.setAdapter(mShoppingTrolleyAdapter);
        initData();
    }

    private void initData() {
        ObservableArrayList<RecyclerData> data = new ObservableArrayList<>();
        data.add(new GoodsTopInfo("荣耀官方旗舰店"));
        data.add(new GoodsCentreInfo("https://img.alicdn.com/bao/uploaded/i5/TB1M4HAoLb2gK0jSZK9ahGEgFXa_041458.jpg_80x80.jpg"
               ,"荣耀V30 pro","5G SA/NSA双模；冰岛幻境；套餐三；8+128G","¥998.99" ));
        data.add(new GoodsCentreInfo("https://img.alicdn.com/bao/uploaded/i5/TB1M4HAoLb2gK0jSZK9ahGEgFXa_041458.jpg_80x80.jpg"
               ,"荣耀V30 pro","天王盖地虎，小鸡炖蘑菇，蘑菇炖不烂，必须加大蒜","¥998.99" ));
        data.add(new GoodsBottomInfo("¥1997.80"));

        data.add(new GoodsTopInfo("荣耀官方旗舰店"));
        data.add(new GoodsCentreInfo("https://img.alicdn.com/bao/uploaded/i5/TB1M4HAoLb2gK0jSZK9ahGEgFXa_041458.jpg_80x80.jpg"
               ,"荣耀V30 pro","5G SA/NSA双模；冰岛幻境；套餐三；8+128G","¥998.99" ));
        data.add(new GoodsCentreInfo("https://img.alicdn.com/bao/uploaded/i5/TB1M4HAoLb2gK0jSZK9ahGEgFXa_041458.jpg_80x80.jpg"
               ,"荣耀V30 pro","天王盖地虎，小鸡炖蘑菇，蘑菇炖不烂，必须加大蒜","¥998.99" ));
        data.add(new GoodsBottomInfo("¥1997.80"));


    mShoppingTrolleyAdapter.refresh(data);
    }
}
