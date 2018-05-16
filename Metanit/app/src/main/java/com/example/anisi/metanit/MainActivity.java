package com.example.anisi.metanit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (ListView)findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.107:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CoursesApi coursesApi = retrofit.create(CoursesApi.class);
                Call<List<Courses>> courses = coursesApi.courses();

                courses.enqueue(new Callback<List<Courses>>() {
                    @Override
                    public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                        ArrayList<String> ar = new ArrayList<String>();
                        if (response.isSuccessful()) {
                            CoursesList cl = new CoursesList();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, CourseTopicsActivity.class);
                            int id = response.body().get(position).id;
                            intent.putExtra("ChosenCourse", id);
                            //запускаем активность лекций
                            startActivity(intent);
                        } else {
                            Log.d("response code " + response.code(),"TagIsSuccessfulElse");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Courses>> call, Throwable t) {
                        Log.d("failure " + t,"TagOnFailure");
                    }
                });


            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);
        Call<List<Courses>> courses = coursesApi.courses();

        courses.enqueue(new Callback<List<Courses>>() {
            @Override
            public void onResponse(Call<List<Courses>> call, Response<List<Courses>> response) {
                if (response.isSuccessful()) {
                    //создание массива дисциплин
                    CoursesList cl = new CoursesList();
                    cl.coursesAR.addAll(response.body());
                    //создание массива с именами дисциплин
                    ArrayList<String> ar = new ArrayList<String>();
                    for (Courses object: cl.coursesAR){
                        ar.add(object.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ar);
                    userList.setAdapter(adapter);

                    Log.d("R -- courses " + response.body().get(0).name,"Msg - IsSuccessful");
                } else {
                    Log.d("response code " + response.code(),"Msg - IsSuccessfulElse");
                }
            }

            @Override
            public void onFailure(Call<List<Courses>> call, Throwable t) {
                Log.d("failure " + t,"TagOnFailure");
            }
        });
    }

    public void onClickMoodle_button1(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MoodleActivity.class);
        intent.putExtra("id", 1000);
        startActivity(intent);
    }
}