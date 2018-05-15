package com.example.anisi.metanit;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

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
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.228:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CoursesApi coursesApi = retrofit.create(CoursesApi.class);
                Call<List<Courses>> courses = coursesApi.courses();

                ArrayList<String> ar1 = new ArrayList<String>();

                courses.enqueue(new Callback<List<Courses>>() {
                    @Override
                    public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                        ArrayList<String> ar = new ArrayList<String>();
                        if (response.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, DetailsActivity.class);
                            //int id = response.body().get(1);
                            //intent.putExtra("id", id);
                            //запускаем вторую активность
                            startActivity(intent);
                        } else {
                            Log.d("response code " + response.code(),"tupoTagIsSuccessfulElse");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Courses>> call, Throwable t) {
                        Log.d("failure " + t,"tupoTagOnFailure");
                    }
                });


            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.228:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);
        Call<List<Courses>> courses = coursesApi.courses();

        ArrayList<String> ar1 = new ArrayList<String>();

        courses.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                ArrayList<String> ar = new ArrayList<String>();
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++){
                        ar.add(response.body().get(i).getName().toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ar);
                    userList.setAdapter(adapter);
                    //создание классов курсов
                    ArrayList<Courses> coursesAR = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++){
                        coursesAR.add(response.body().get(i));
                        Log.d("response courses #" + i +" = " + coursesAR.get(i).getName(),"");
                    }
                    Log.d("response " + response.body().get(0).getName(),"tupoTagIsSuccessful");
                } else {
                    Log.d("response code " + response.code(),"tupoTagIsSuccessfulElse");
                }
            }

            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Log.d("failure " + t,"tupoTagOnFailure");
            }
        });
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.id.list, coursesList);
        /*
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
        */
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        //db.close();
        //userCursor.close();
    }

    public void onClickMoodle_button1(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MoodleActivity.class);
        intent.putExtra("id", 1000);
        startActivity(intent);
    }
}