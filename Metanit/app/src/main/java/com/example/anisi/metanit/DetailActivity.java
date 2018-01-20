package com.example.anisi.metanit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DetailActivity extends AppCompatActivity {

    TextView userText;
    TextView userText2;
    TextView userText3;
    ImageView photo;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    StringBuilder text = new StringBuilder();
    int k;
    AlertDialog.Builder ad;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = DetailActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userText = (TextView)findViewById(R.id.textView);
        userText2 = (TextView)findViewById(R.id.textView2);
        userText3 = (TextView)findViewById(R.id.textView3);
        photo = (ImageView)findViewById(R.id.imageView);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        Intent intent = getIntent();
        k = intent.getIntExtra("id",0);
    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        userCursor =  db.query("REFERENCE_BOOK",
                new String[] {"NAME", "DESC", "TYPE", "IMAGE_ID"},
                "_id = ?", new String[] {Integer.toString(k+1)},
                null, null, null);

        if(userCursor.moveToFirst()) {
            String nameText = userCursor.getString(0);
            String descId = userCursor.getString(1);
            String typeText = userCursor.getString(2);
            int photoId = userCursor.getInt(3);

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(getAssets().open(descId)));
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    text.append(mLine);
                    text.append('\n');
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }

            userText.setText(nameText);
            userText2.setText((CharSequence) text);;
            userText3.setText(typeText);
            photo.setImageResource(photoId);
            photo.setContentDescription(nameText);
        }
        userCursor.close();
        db.close();
    }
    public void onClickPrev_button(View view) {
        if(k < 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
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
            intent.setClass(DetailActivity.this, DetailActivity.class);
            intent.putExtra("id", k - 1);
            startActivity(intent);
            this.finish();
        }
    }

    public void onClickNext_button(View view) {
        Intent intent = new Intent();
        if(k>6){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
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
            intent.setClass(DetailActivity.this, DetailActivity.class);
            intent.putExtra("id", k + 1);
            startActivity(intent);
            this.finish();
        }
    }

    public void onClickMoodle_button2(View view) {
        Intent intent = new Intent();
        intent.setClass(DetailActivity.this, MoodleActivity.class);
        intent.putExtra("id", k);
        startActivity(intent);
        this.finish();
    }
  /*  @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }  */
}
