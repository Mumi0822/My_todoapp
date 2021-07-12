package com.example.my_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    //タスク新規及び更新画面の設定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //MainActivityからのモード指定：0文字なら新規タスク
        Intent intentMain = getIntent();
        String flag = intentMain.getStringExtra("FLG");
        //新規か更新かで内容を変える部品
        TextView label1 = findViewById(R.id.textView);
        View view1 = findViewById(R.id.Layout1);


        if(flag.length()!=0 ){
        //更新
        }else{
        //新規
            label1.setText("ADD TASK");
            view1.setBackgroundColor(Color.parseColor("#FFC0FFC0"));
        }
    }
    //戻るボタンのonclick
    public void onclose(View view) {
        finish();
    }
}