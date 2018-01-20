package com.example.anisi.metanit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.content.Intent;

public class MoodleActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();

        Intent intent = new Intent();
        int j = intent.getIntExtra("id",0);
        if (j > 999) {
            String url = "http://yagu.s-vfu.ru/course/view.php?id=5124";
            setContentView(R.layout.activity_moodle);
            WebView webView = (WebView) findViewById(R.id.webview);
            webView.loadUrl(url);
            this.finish();
        }
        else{
            userCursor = db.query("REFERENCE_BOOK",
                    new String[]{"URL"},
                    "_id = ?", new String[]{Integer.toString(j + 1)},
                    null, null, null);
            if (userCursor.moveToFirst()) {
                String url = userCursor.getString(0);
                setContentView(R.layout.activity_moodle);
                WebView webView = (WebView) findViewById(R.id.webview);
                webView.loadUrl(url);
                this.finish();
            }
        }
    }
}
