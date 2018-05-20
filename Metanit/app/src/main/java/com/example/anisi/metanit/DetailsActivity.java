package com.example.anisi.metanit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import java.util.ArrayList;

import us.feras.mdv.MarkdownView;

public class DetailsActivity extends AppCompatActivity {

    int chosenTopic;
    ArrayList<CourseTopic> courseTopicArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent intent = getIntent();
//        int
//    }
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
