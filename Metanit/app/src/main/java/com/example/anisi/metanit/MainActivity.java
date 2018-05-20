package com.example.anisi.metanit;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    ArrayList<Course> courseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (ListView)findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CourseTopicsActivity.class);
                int courseid = courseArrayList.get(position).id;
                intent.putExtra("ChosenCourseId", courseid);
                intent.putParcelableArrayListExtra(Course.class.getCanonicalName(), courseArrayList);
                //запускаем активность лекций
                startActivity(intent);
            }
        });
    }
    
    @Override
    public void onResume() {
        super.onResume();

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (Utils.isNetworkAvailable(getApplicationContext())) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);
        Call<List<Course>> courses = coursesApi.course();

        courses.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    //создание массива дисциплин
                    courseArrayList.addAll(response.body());
                    //создание массива с именами дисциплин
                    ArrayList<String> ar = new ArrayList<String>();
                    for (Course object: courseArrayList){
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
            public void onFailure(Call<List<Course>> call, Throwable t) {
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