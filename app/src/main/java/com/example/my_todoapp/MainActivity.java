package com.example.my_todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.my_todoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ListView allTaskList;

    //起動時の表示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allTaskList = findViewById(R.id.Listview);

        MyOpenHelper myOpenHelper = new MyOpenHelper(this);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        Cursor c = db.rawQuery("select * from mytaskdb",null);

        String[] from = {"_id","name"};

        int[] to = {android.R.id.text1,android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,c,from,to,0);
        allTaskList.setAdapter(adapter);

        allTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //各要素を取得
                //_id
                String s1 = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();

                //参照・更新へ
                Intent intent = new Intent(getApplication(),SettingActivity.class);

                //モード指定　_idを渡す
                intent.putExtra("MODE",s1);

                //行く
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        reload();
    }

    public void register(View view) {
        Intent intent = new Intent(getApplication(),SettingActivity.class);
        intent.putExtra("MODE","");
        startActivity(intent);
    }
    public void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }
}