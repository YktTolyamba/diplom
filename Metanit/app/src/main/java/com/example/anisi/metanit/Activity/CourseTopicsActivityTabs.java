//package com.example.anisi.metanit.Activity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TabHost;
//import android.widget.TextView;
//
//import com.example.anisi.metanit.Course;
//import com.example.anisi.metanit.CourseTopic;
//import com.example.anisi.metanit.R;
//import com.example.anisi.metanit.Tag;
//
//import java.util.ArrayList;
//
//import static com.example.anisi.metanit.R.id.listTag;
//import static com.example.anisi.metanit.R.id.listTopic;
//
//public class CourseTopicsActivityTabs extends AppCompatActivity {
//
//    String TAG = "CourseTopicActivity";
//    ListView topicList;
//    ListView tagList;
//    ArrayList<Course> courseArrayList = new ArrayList<>();
//    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();
//    ArrayList<Tag> tagArrayList = new ArrayList<>();
//    ArrayList<CourseTopic> courseTopicArrayList1 = new ArrayList<>();
//    ArrayList<CourseTopic> courseTopicArrayListTagged = new ArrayList<>();
//    TabHost tabHost;
//    int tagInt = 0;
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                CourseTopicsActivityTabs.this.onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    public void setTopicListInfo(ArrayList<CourseTopic> arrayList){
//        topicList = (ListView)findViewById(listTopic);
//        ArrayList<String> courseTopicNames = new ArrayList<>();
//        for (CourseTopic object : arrayList) {
//            courseTopicNames.add(object.name);
//        }
//        ArrayAdapter<String> adapter;
//        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, courseTopicNames){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent){
//                // Get the Item from ListView
//                View view = super.getView(position, convertView, parent);
//
//                // Initialize a TextView for ListView each Item
//                TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                // Set the text color of TextView (ListView Item)
//                tv.setTextColor(Color.BLACK);
//
//                // Generate ListView Item using TextView
//                return view;
//            }
//        };
//        topicList.setAdapter(adapter);
//
//    }
//
//    public void setTagListInfo(ArrayList<Tag> arrayList){
//        tagList = (ListView)findViewById(listTag);
//        ArrayList<String> courseTopicNames = new ArrayList<>();
//        for (Tag object : arrayList) {
//            courseTopicNames.add(object.name);
//        }
//        ArrayAdapter<String> adapter;
//        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, courseTopicNames){
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent){
//                // Get the Item from ListView
//                View view = super.getView(position, convertView, parent);
//
//                // Initialize a TextView for ListView each Item
//                TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                // Set the text color of TextView (ListView Item)
//                tv.setTextColor(Color.BLACK);
//
//                // Generate ListView Item using TextView
//                return view;
//            }
//        };
//        tagList.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_course_topics);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        topicList = (ListView)findViewById(listTopic);
//        tagList = (ListView)findViewById(listTag);
//        tabHost = (TabHost) findViewById(android.R.id.tabhost);
//        Log.d("CourseTopicActivity","onCreate: create");
//
//        // инициализация
//        tabHost.setup();
//
//        TabHost.TabSpec tabSpec;
//
//        // создаем вкладку и указываем тег
//        tabSpec = tabHost.newTabSpec("tag1");
//        // название вкладки
//        tabSpec.setIndicator("Лекции");
//        // указываем id компонента из FrameLayout, он и станет содержимым
//        tabSpec.setContent(R.id.tab1);
//        // добавляем в корневой элемент
//        tabHost.addTab(tabSpec);
//
//        tabSpec = tabHost.newTabSpec("tag2");
//        // указываем название и картинку
//        // в нашем случае вместо картинки идет xml-файл,
//        // который определяет картинку по состоянию вкладки
//        tabSpec.setIndicator("Тэги", getResources().getDrawable(R.drawable.tab_icon_selector));
//        tabSpec.setContent(R.id.tab2);
//        tabHost.addTab(tabSpec);
//
//        // первая вкладка будет выбрана по умолчанию
//        tabHost.setCurrentTabByTag("tag1");
//
//        // обработчик переключения вкладок
//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//          public void onTabChanged(String tabId) {
////                if (tabId.equals("tag1")){
////                    setTopicListInfo(topicList, courseTopicArrayListTagged);
////                }
//           }
//        });
//
//        //получение id выбранного курса
//        final Intent intent = getIntent();
//        String courseUrl = intent.getStringExtra("ChosenCourseUrl");
//        String chosenTag = intent.getStringExtra("ChosenTag");
//        courseArrayList = getIntent().getParcelableArrayListExtra(Course.class.getCanonicalName());
//        courseTopicArrayList = getIntent().getParcelableArrayListExtra(CourseTopic.class.getCanonicalName());
//        tagArrayList = getIntent().getParcelableArrayListExtra(Tag.class.getCanonicalName());
//
//        //фильтр топиков по курсам
//        for (CourseTopic object: courseTopicArrayList) {
//            if (object.course.equals(courseUrl)){
//                courseTopicArrayList1.add(object);
//            }
//        }
//
//        courseTopicArrayListTagged.addAll(courseTopicArrayList1);
//        setTopicListInfo(courseTopicArrayListTagged);
//        setTagListInfo(tagArrayList);
//
//        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                intent.setClass(CourseTopicsActivityTabs.this, DetailsActivity.class);
//                int chosenTopicsCode = position + 1;
//                intent.putExtra("ChosenTopicsCode", chosenTopicsCode);
//                intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayListTagged);
//                //запускаем активность лекции
//                startActivity(intent);
//            }
//        });
//
//        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                intent.setClass(CourseTopicsActivityTabs.this, CourseTopicsActivityTabs.class);
//                int chosenTag = tagArrayList.get(position).id;
//                String tagUrl = "http://192.168.0.107:8000/tag/" + chosenTag + "/";
//
//                courseTopicArrayListTagged.clear();
//                for (CourseTopic object: courseTopicArrayList1) {
//                    if (object.tag.contains(tagUrl)){
//                        courseTopicArrayListTagged.add(object);
//                    }
//                }
//                setTopicListInfo(courseTopicArrayListTagged);
//                tabHost.setCurrentTabByTag("tag1");
//            }
//        });
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("CourseTopicActivity", "onRestart");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("CourseTopicActivity", "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("CourseTopicActivity", "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("CourseTopicActivity", "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("CourseTopicActivity", "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("CourseTopicActivity", "onDestroy");
//    }
//}
