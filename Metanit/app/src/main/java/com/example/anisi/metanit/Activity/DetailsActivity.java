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

    int chosenTopic;
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                DetailsActivity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("DetailsActivity", "onCreate: create");
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        Intent intent = getIntent();
        String chosenTopic = intent.getStringExtra("ChosenTopicsCode");
        courseTopicArrayList = getIntent().getParcelableArrayListExtra(CourseTopic.class.getCanonicalName());
        Log.d("PROVERKA intent = " + chosenTopic," AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        String MDtext = "Ошибка загрузки текста";
        for (int i = 0; i < courseTopicArrayList.size(); i++){
            if (courseTopicArrayList.get(i).code.equals(chosenTopic)){
                MDtext = courseTopicArrayList.get(i).text;
            }
        }
        markdownView.loadMarkdown(MDtext);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DetailsActivity", "onStart: start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DetailsActivity", "onResume: resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DetailsActivity", "onPause: pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DetailsActivity", "onStop: stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DetailsActivity", "onDestroy: destroy");
    }
    public void onClickPrev_button(View view) {
        if(chosenTopic < 1){
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
            intent.putExtra("id", chosenTopic - 1);
            startActivity(intent);
            this.finish();
        }
    }

    public void onClickNext_button(View view) {
        Intent intent = new Intent();
        if(chosenTopic>14){
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
            intent.putExtra("id", chosenTopic + 1);
            startActivity(intent);
            this.finish();
        }
    }
}
