package com.example.anisi.metanit.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;

import com.example.anisi.metanit.CourseTopic;
import com.example.anisi.metanit.R;

import java.util.ArrayList;

import us.feras.mdv.MarkdownView;

public class DetailsActivity extends AppCompatActivity {

    String TAG = "DetailsActivity";
    int topicsCodeInt;
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DetailsActivity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getTopicText(String code){
        String topicText = "Ошибка загрузки текста";
        courseTopicArrayList = getIntent().getParcelableArrayListExtra(CourseTopic.class.getCanonicalName());
        for (int i = 0; i < courseTopicArrayList.size(); i++) {
            if (courseTopicArrayList.get(i).code.equals(code)) {
                topicText = courseTopicArrayList.get(i).text;
            }
        }
        return topicText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("DetailsActivity", "onCreate: create");
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        Intent intent = getIntent();
        topicsCodeInt = intent.getIntExtra("ChosenTopicsCode",0);
        String topicsCodeString = Integer.toString(topicsCodeInt);
        Log.d(TAG,"ChosenTopicCode = " + topicsCodeString);
        String MDtext = getTopicText(topicsCodeString);
        markdownView.loadMarkdown(MDtext);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
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

    public void onClickPrev_button(View view) {
        if(topicsCodeInt == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle("Это первая лекция")
                    .setIcon(R.drawable.n4)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            //this.finish();
        }
        else {
            Intent intent = new Intent();
            intent.setClass(DetailsActivity.this, DetailsActivity.class);
            intent.putExtra("ChosenTopicsCode", topicsCodeInt - 1);
            intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList);
            startActivity(intent);
            this.finish();
        }
    }

    public void onClickNext_button(View view) {
        Intent intent = new Intent();
        if(topicsCodeInt == courseTopicArrayList.size()){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle("Это последняя лекция")
                    .setIcon(R.drawable.n4)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            //this.finish();
        }
        else {
            intent.setClass(DetailsActivity.this, DetailsActivity.class);
            intent.putExtra("ChosenTopicsCode", topicsCodeInt + 1);
            intent.putParcelableArrayListExtra(CourseTopic.class.getCanonicalName(), courseTopicArrayList);
            startActivity(intent);
            this.finish();
        }
    }
}
