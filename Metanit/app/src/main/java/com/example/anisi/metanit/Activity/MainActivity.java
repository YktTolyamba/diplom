package com.example.anisi.metanit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.anisi.metanit.Course;
import com.example.anisi.metanit.CourseTopic;
import com.example.anisi.metanit.CourseTopicApi;
import com.example.anisi.metanit.CoursesApi;
import com.example.anisi.metanit.R;
import com.example.anisi.metanit.Tag;
import com.example.anisi.metanit.TagApi;
import com.example.anisi.metanit.Utils;

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

import javax.crypto.NullCipher;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ListView userList;
    ArrayList<Course> courseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");

        ////////////////////////COURSE////CACHE////////////////////////
        OkHttpClient clientCourse = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(clientCourse)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoursesApi coursesApi = retrofit.create(CoursesApi.class);
        Call<List<Course>> courses = coursesApi.course();

        courses.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG,"Courses - success");
                } else {
                    Log.d(TAG,"Courses - not success");
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d(TAG,"Courses - failure");
            }
        });

        ////////////////////////TOPIC////CACHE////////////////////////
        OkHttpClient clientTopic = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(clientTopic)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseTopicApi courseTopicApi = retrofit2.create(CourseTopicApi.class);
        Call<List<CourseTopic>> courseTopics = courseTopicApi.courseTopics();

        courseTopics.enqueue(new Callback<List<CourseTopic>>() {
            @Override
            public void onResponse(Call<List<CourseTopic>> call, Response<List<CourseTopic>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG,"Topics - success");
                } else {
                    Log.d(TAG,"Topics - not success");
                }
            }
            @Override
            public void onFailure(Call<List<CourseTopic>> call, Throwable t) {
                Log.d(TAG,"Topics - failure");
            }
        });

        ////////////////////////TAG////CACHE////////////////////////
        OkHttpClient clientTag = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(clientTag)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TagApi tagApi = retrofit3.create(TagApi.class);
        Call<List<Tag>> tag = tagApi.tag();

        tag.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG,"Tag - success");
                } else {
                    Log.d(TAG,"Tag - not success");
                }
            }
            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.d(TAG,"Tag - failure");
            }
        });

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

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit4 = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coursesApi = retrofit4.create(CoursesApi.class);
        courses = coursesApi.course();

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
                    ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, ar);
                    userList.setAdapter(adapter);
                    //Log.d("R -- courses " + response.body().get(0).name,"Msg - IsSuccessful");
                } else {
                    //Log.d("response code " + response.code(),"Msg - IsSuccessfulElse");
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d("failure " + t,"TagOnFailure");
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
//    public void onClickMoodle_button1(View view) {
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, MoodleActivity.class);
//        intent.putExtra("id", 1000);
//        startActivity(intent);
//    }
}