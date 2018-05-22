package com.example.anisi.metanit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.anisi.metanit.Utils.isNetworkAvailable;

public class CourseTopicsActivity extends AppCompatActivity {

    ListView TopicList;
    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_topics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TopicList = (ListView)findViewById(R.id.list);

        //получение id выбранного курса
        final Intent intent = getIntent();
        final int chosenCourse = intent.getIntExtra("ChosenCourseId",0);
        courseArrayList = getIntent().getParcelableArrayListExtra(Course.class.getCanonicalName());

        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (isNetworkAvailable(getApplicationContext())) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.228:8000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseTopicApi courseTopicApi = retrofit.create(CourseTopicApi.class);
        Call<List<CourseTopic>> courseTopics = courseTopicApi.courseTopic(chosenCourse);

        courseTopics.enqueue(new Callback<List<CourseTopic>>() {
            @Override
            public void onResponse(Call<List<CourseTopic>> call, Response<List<CourseTopic>> response) {
                if (response.isSuccessful()) {
                    //создание массива лекций дисциплин
                    courseTopicArrayList.addAll(response.body());
                    //создание массива с именами лекций дисциплин
                    ArrayList<String> ar = new ArrayList<String>();
                    for (CourseTopic object: courseTopicArrayList) {
                        ar.add(object.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ar);
                    TopicList.setAdapter(adapter);

                    //Log.d("R -- topics " + response.body().get(1).name,"Msg - IsSuccessful");
                } else {
                    Log.d("response code " + response.code(),"Msg - IsSuccessfulElse");
                }
            }

            @Override
            public void onFailure(Call<List<CourseTopic>> call, Throwable t) {
                Log.d("failure " + t," Failure");
            }
        });

        TopicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.setClass(CourseTopicsActivity.this, DetailsActivity.class);
                String positionStr = Integer.toString(position+1);
                intent.putExtra("ChosenTopicsCode", positionStr);
                intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList);
                //запускаем активность лекции
                startActivity(intent);
            }
        });
    }
}
