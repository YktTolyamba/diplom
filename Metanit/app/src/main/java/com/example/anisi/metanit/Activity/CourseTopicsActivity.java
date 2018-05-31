package com.example.anisi.metanit.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anisi.metanit.Course;
import com.example.anisi.metanit.CourseTopic;
import com.example.anisi.metanit.R;
import com.example.anisi.metanit.Tag;

import java.util.ArrayList;

import static com.example.anisi.metanit.R.id.listTopic;

public class CourseTopicsActivity extends AppCompatActivity {

    String TAG = "CourseTopicActivity";
    ListView topicList;
    ListView tagList;
    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();
    ArrayList<Tag> tagArrayList = new ArrayList<>();
    ArrayList<CourseTopic> courseTopicArrayList1 = new ArrayList<>();

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
        topicList = (ListView)findViewById(listTopic);
        Log.d(TAG,"onCreate: create");

        //получение id выбранного курса
        final Intent intent = getIntent();
        String courseUrl = intent.getStringExtra("ChosenCourseUrl");
        courseArrayList = getIntent().getParcelableArrayListExtra(Course.class.getCanonicalName());
        courseTopicArrayList = getIntent().getParcelableArrayListExtra(CourseTopic.class.getCanonicalName());
        tagArrayList = getIntent().getParcelableArrayListExtra(Tag.class.getCanonicalName());

        //фильтр топиков по курсам
        for (CourseTopic object: courseTopicArrayList) {
            if (object.course.equals(courseUrl)){
                courseTopicArrayList1.add(object);
            }
        }

        ArrayList<String> courseTopicNames = new ArrayList<>();
        for (CourseTopic object : courseTopicArrayList1) {
            courseTopicNames.add(object.name);
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, courseTopicNames){
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
        topicList.setAdapter(adapter);

        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intent.setClass(CourseTopicsActivity.this, DetailsActivity.class);
                int chosenTopicsCode = position + 1;
                intent.putExtra("ChosenTopicsCode", chosenTopicsCode);
                intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList1);
                //запускаем активность лекции
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "onRestart");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }
}