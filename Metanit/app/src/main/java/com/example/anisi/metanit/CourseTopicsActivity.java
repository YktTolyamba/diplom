package com.example.anisi.metanit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseTopicsActivity extends AppCompatActivity {

    ListView TopicsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_topics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TopicsList = (ListView)findViewById(R.id.list);

        //получение id выбранного курса
        final Intent intent = getIntent();
        final int chosenCourse = intent.getIntExtra("ChosenCourse",0);

        TopicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CourseTopicsList ctl = new CourseTopicsList();
                intent.setClass(CourseTopicsActivity.this, DetailsActivity.class);
                int chosenTopic = 0;
                String chCourse = Integer.toString(chosenCourse);
                String positionStr = Integer.toString(position+1);
                Log.d("PROVERKA = " + chCourse + ", " + positionStr , " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                for (CourseTopics object: ctl.coursesTopicsAR) {
                    Log.d("FOR AAAAA" + object.course + ", " + object.code, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    if ((object.course.equals(chCourse)) && (object.code.equals(positionStr))){
                        chosenTopic = object.id;
                        Log.d("SUCCESSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSS");
                    }
                }
                intent.putExtra("ChosenTopic", chosenTopic);
                //запускаем активность лекции
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseTopicsApi courseTopicsApi = retrofit.create(CourseTopicsApi.class);
        Call<List<CourseTopics>> courseTopics = courseTopicsApi.courseTopics(chosenCourse);

        courseTopics.enqueue(new Callback<List<CourseTopics>>() {
            @Override
            public void onResponse(Call<List<CourseTopics>> call, Response<List<CourseTopics>> response) {
                if (response.isSuccessful()) {
                    //создание массива лекций дисциплин
                    CourseTopicsList ctl = new CourseTopicsList();
                    ctl.coursesTopicsAR.addAll(response.body());
                    //создание массива с именами лекций дисциплин
                    ArrayList<String> ar = new ArrayList<String>();
                    for (CourseTopics object: ctl.coursesTopicsAR) {
                        ar.add(object.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ar);
                    TopicsList.setAdapter(adapter);

                    Log.d("R -- topics " + response.body().get(0).name,"Msg - IsSuccessful");
                } else {
                    Log.d("response code " + response.code(),"Msg - IsSuccessfulElse");
                }
            }

            @Override
            public void onFailure(Call<List<CourseTopics>> call, Throwable t) {
                Log.d("failure " + t,"TagOnFailure");
            }
        });
    }
}
