package com.example.anisi.metanit.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.NullCipher;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ListView userList;
    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();
    ArrayList<Tag> tagArrayList = new ArrayList<>();
    boolean serverConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");

        //проверка связи с сервером, если нет, то клиенту Okhttp будет передано, чтобы он в этом случае использовал кэш
        final Retrofit proverkaServera = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CoursesApi coursesApiProverka = proverkaServera.create(CoursesApi.class);
        Call<List<Course>> coursesProverka = coursesApiProverka.course();

        coursesProverka.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    serverConnect = true;
                    Log.d(TAG,"Connection to server - success");
                } else {
                    Log.d(TAG,"Connection to server - not success");
                    serverConnect = false;
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d(TAG,"Connection to server - failure");
                serverConnect = false;
            }
        });

        //прием данных и кэширование
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 50*1024*1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(Utils.isNetworkAvailable(getApplicationContext()) || !(InetAddress.getByName("192.168.0.107:8000")).isReachable(500)) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //прием данных дисциплин
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
                    ArrayAdapter<String> adapter;
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, ar){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.BLACK);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    userList.setAdapter(adapter);

                    //Прием данных лекций в цикле
                    for (int i = 1; i <= courseArrayList.size(); i++) {
                        CourseTopicApi courseTopicApi = retrofit.create(CourseTopicApi.class);
                        Call<List<CourseTopic>> courseTopics = courseTopicApi.courseTopic(i);

                        courseTopics.enqueue(new Callback<List<CourseTopic>>() {
                            @Override
                            public void onResponse(Call<List<CourseTopic>> call, Response<List<CourseTopic>> response) {
                                if (response.isSuccessful()) {
                                    courseTopicArrayList.addAll(response.body());
                                    Log.d(TAG, "Topic - success");
                                } else {
                                    Log.d(TAG, "Topic - not success");
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CourseTopic>> call, Throwable t) {
                                Log.d(TAG, "Topics - failure");
                            }
                        });
                    }

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

        //прием данных тегов
        TagApi tagApi = retrofit.create(TagApi.class);
        Call<List<Tag>> tag = tagApi.tag();

        tag.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    tagArrayList.addAll(response.body());
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
                String courseUrl = courseArrayList.get(position).url;
                String chosenTag = "НетТега";
                intent.putExtra("ChosenCourseUrl", courseUrl);
                intent.putParcelableArrayListExtra(Course.class.getCanonicalName(), courseArrayList);
                intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList);
                intent.putParcelableArrayListExtra(Tag.class.getCanonicalName(), tagArrayList);
                intent.putExtra("ChosenTag", chosenTag);
                //запускаем активность лекций
                startActivity(intent);
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
}