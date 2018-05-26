package com.example.anisi.metanit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.anisi.metanit.Course;
import com.example.anisi.metanit.CourseTopic;
import com.example.anisi.metanit.CourseTopicApi;
import com.example.anisi.metanit.R;
import com.example.anisi.metanit.Tag;
import com.example.anisi.metanit.TagApi;

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

public class CourseTopicsActivity extends AppCompatActivity {

    String TAG = "CourseTopicActivity";
    ListView topicList;
    ListView tagList;
    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();
    ArrayList<Tag> tagArrayList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CourseTopicsActivity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_topics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("CourseTopicActivity","onCreate: create");



        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("Лекции");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab1);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        // указываем название и картинку
        // в нашем случае вместо картинки идет xml-файл,
        // который определяет картинку по состоянию вкладки
        tabSpec.setIndicator("Тэги", getResources().getDrawable(R.drawable.tab_icon_selector));
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        // первая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("tag1");

        // обработчик переключения вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });

        //получение id выбранного курса
        final Intent intent = getIntent();
        int courseId = intent.getIntExtra("ChosenCourseId",0);
        String courseIdString = Integer.toString(courseId);
        String chosenTag = intent.getStringExtra("ChosenTag");
        courseArrayList = getIntent().getParcelableArrayListExtra(Course.class.getCanonicalName());
        courseTopicArrayList = getIntent().getParcelableArrayListExtra(CourseTopic.class.getCanonicalName());
        tagArrayList = getIntent().getParcelableArrayListExtra(Tag.class.getCanonicalName());
        Log.d(TAG," Intent course = " + courseId);
        Log.d(TAG," Intent courseString = " + courseIdString);
        Log.d(TAG," Intent chosenTag = " + chosenTag);

        ArrayList<CourseTopic> courseTopicArrayList1 = new ArrayList<CourseTopic>();
        for (CourseTopic object: courseTopicArrayList) {
            if (object.course.equals(courseIdString)){
                courseTopicArrayList1.add(object);
            }
        }
        ArrayList<String> courseTopicNames = new ArrayList<String>();

        if (chosenTag.equals("НетТега")){
            Log.d(TAG,"По ифу зашел в нет тега");
            for (CourseTopic object: courseTopicArrayList1){
                courseTopicNames.add(object.name);
                Log.d(TAG," object.name" + object.name);
            }
        } else {
            Log.d(TAG,"По ифу зашел в есть тег");
            ArrayList<CourseTopic> courseTopicArrayList2 = new ArrayList<CourseTopic>();
            for (CourseTopic object: courseTopicArrayList1) {
                if (object.tag.contains(chosenTag)){
                    courseTopicArrayList2.add(object);
                    courseTopicNames.add(object.name);
                }
            }
        }

        topicList = (ListView)findViewById(R.id.listTopic);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, courseTopicNames);
        topicList.setAdapter(adapter);

        tagList = (ListView)findViewById(R.id.listTag);
        ArrayList<String> tagnames = new ArrayList<String>();
        for (Tag object: tagArrayList) {
            tagnames.add(object.name);
        }
        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tagnames);
        tagList.setAdapter(adapter2);

        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.setClass(CourseTopicsActivity.this, DetailsActivity.class);
                int chosenTopicsCode = 0;
                chosenTopicsCode = position + 1;
                intent.putExtra("ChosenTopicsCode", chosenTopicsCode);
                intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList);
                //запускаем активность лекции
                startActivity(intent);
            }
        });

        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.setClass(CourseTopicsActivity.this, CourseTopicsActivity.class);
                String chosenTag = "НетТега";
                chosenTag = tagArrayList.get(position).name;
                intent.putExtra("ChosenTag", chosenTag);
                //запускаем активность лекции
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CourseTopicActivity", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CourseTopicActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CourseTopicActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CourseTopicActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CourseTopicActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CourseTopicActivity", "onDestroy");
    }
}
