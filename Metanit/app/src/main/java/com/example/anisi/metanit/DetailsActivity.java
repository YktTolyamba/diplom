package com.example.anisi.metanit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.util.Log;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    Context context;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView webView = (WebView) findViewById(R.id.webView);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        Intent intent = getIntent();
        k = intent.getIntExtra("id", 0);
        context = getBaseContext();
        userCursor =  db.query("REFERENCE_BOOK",
                new String[] {"DESC"},
                "_id = ?", new String[] {Integer.toString(k+1)},
                null, null, null);
        String resName = "";
        if(userCursor.moveToFirst()) {
            resName = userCursor.getString(0);
            String text = readRawTextFile(context, getResources().getIdentifier(resName, "raw", "com.example.anisi.metanit"));
            webView.loadDataWithBaseURL(null, text, "text/html", "en_US", null);
        }
    }

    public static String readRawTextFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader);
        String line;
        StringBuilder builder = new StringBuilder();

        try {
            while ((line = buffReader.readLine()) != null) {
                builder.append(line);
                builder.append("<br />");
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }

    public void onClickPrev_button(View view) {
        if(k < 1){
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
            intent.putExtra("id", k - 1);
            startActivity(intent);
            this.finish();
        }
    }

    public void onClickNext_button(View view) {
        Intent intent = new Intent();
        if(k>14){
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
            intent.putExtra("id", k + 1);
            startActivity(intent);
            this.finish();
        }
    }
}
