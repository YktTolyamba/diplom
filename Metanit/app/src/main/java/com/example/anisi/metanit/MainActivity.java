package com.example.anisi.metanit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (ListView)findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailsActivity.class);

                intent.putExtra("id", position);
                //запускаем вторую активность
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }

    public void onClickMoodle_button1(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MoodleActivity.class);
        intent.putExtra("id", 1000);
        startActivity(intent);
    }
}