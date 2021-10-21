package com.example.my_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    //タスク新規及び更新画面の設定
    private MyOpenHelper helper;
    String mode = "";
    String Message1 = "Task was added.";
    String Message2 = "Please enter taskname.";
    String Message3 = "Updated";
    String Message4 = "Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //
        helper = new MyOpenHelper(getApplicationContext());

        //MainActivityからのモード指定：0文字なら新規タスク
        Intent intent = getIntent();
        String MODE = intent.getStringExtra("MODE");

        //新規か更新かで内容を変える部品
        TextView label1 = findViewById(R.id.textView);
        View view1 = findViewById(R.id.Layout1);
        ImageButton button= findViewById(R.id.imageButton);



        if(MODE.length()!=0 ){
        //更新
            mode = MODE;
            label1.setText("EDIT TASK");
            view1.setBackgroundColor(Color.parseColor("#FFC0FFFF"));
            readData(mode);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delData();
                    finish();
                }
            });

        }else{
        //新規
            mode = "ADD_TASK";
            label1.setText("ADD TASK");
            view1.setBackgroundColor(Color.parseColor("#FFC0FFC0"));
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void saveData(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        EditText text1 = findViewById(R.id.Taskname);
        EditText text2 = findViewById(R.id.Taskcate);
        EditText text3 = findViewById(R.id.Tasklevel);
        String name = text1.getText().toString();
        String cate = text2.getText().toString();
        String level = text3.getText().toString();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("cate", cate);
        values.put("level", level);

        if(mode=="ADD_TASK"){
            if(name.length() != 0){
                //新規登録
                db.insert("mytaskdb", null, values);
                //トースト表示
                newToast(Message1, 0, +400);
                finish();
            }else{
                //トースト表示
                newToast(Message2, 0, +400);
            }

            //ボタンが更新の場合
        }else{
            if(name.length() != 0){
                //更新
                UPData(mode);
                //トースト表示
                newToast(Message3, 0, +400);
                finish();
            }else{
                //トースト表示
                newToast(Message4, 0, +400);
            }
        }
    }
    public void readData(String read){
        SQLiteDatabase db = helper.getReadableDatabase();

        EditText text1 = findViewById(R.id.Taskname);
        EditText text2 = findViewById(R.id.Taskcate);
        EditText text3 = findViewById(R.id.Tasklevel);

        Cursor cursor = db.query(
                "mytaskdb",
                new String[]{"name","cate","level" },
                "_ID = ?",
                new String[]{read},
                null,null,null
        );
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            text1.setText(cursor.getString(0));
            text2.setText(cursor.getString(1));
            text3.setText(cursor.getString(2));
        }

        cursor.close();

    }
    public void UPData(String read){
        SQLiteDatabase db = helper.getReadableDatabase();

        EditText txt1 = findViewById(R.id.Taskname);
        EditText txt2 = findViewById(R.id.Taskcate);
        EditText txt3 = findViewById(R.id.Tasklevel);

        String name = txt1.getText().toString();
        String cate = txt2.getText().toString();
        String level = txt3.getText().toString();

        ContentValues upvalues = new ContentValues();
        upvalues.put("name",name);
        upvalues.put("cate",cate);
        upvalues.put("level",level);


        db.update("mytaskdb",upvalues,"_id=?",new String[]{read});


    }
    private void newToast(String message, int x, int y){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        // 位置調整
        toast.setGravity(Gravity.CENTER, x, y);
        toast.show();
    }

    //戻るボタンのonclick
    public void onclose(View view) {
        finish();
    }

    //task削除ボタン
    public void delData() {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("mytaskdb","_id=?",new String[]{mode});
    }
}