package com.iflytek.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSimple;
    private Button mBtnSingleType;
    private Button mBtnMultiType;
    private Button mBtnSelect;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnSimple = findViewById(R.id.btn_simple);
        mBtnSingleType = findViewById(R.id.btn_single_type);
        mBtnMultiType = findViewById(R.id.btn_multi_type);
        mBtnSelect = findViewById(R.id.btn_select);

        mBtnSimple.setOnClickListener(this);
        mBtnSingleType.setOnClickListener(this);
        mBtnMultiType.setOnClickListener(this);
        mBtnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simple:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case R.id.btn_single_type:
                startActivity(new Intent(this, SingleTypeActivity.class));
                break;
            case R.id.btn_multi_type:
                startActivity(new Intent(this, MultiTypeActivity.class));
                break;
            case R.id.btn_select:
                startActivity(new Intent(this, SelectImageActivity.class));
                break;

            default:
                break;
        }
    }
}
