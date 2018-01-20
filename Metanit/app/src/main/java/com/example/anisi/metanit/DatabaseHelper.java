package com.example.anisi.metanit;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reference_book.db"; // название бд
    private static final int SCHEMA = 31; // версия базы данных
    static final String TABLE = "REFERENCE_BOOK"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IMAGE_ID = "image_id";
    public static final String COLUMN_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE REFERENCE_BOOK (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_DESC + " TEXT," + COLUMN_TYPE + " TEXT, " + COLUMN_IMAGE_ID + " INTEGER, " + COLUMN_URL + " TEXT);");

        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('1. Построение программ', 'lect1', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37007/');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('2. Базовые типы данных', 'lect2', 'Лекция', " + R.drawable.n4 + ",'http://yagu.s-vfu.ru/mod/resource/view.php?id=37009');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('3.Программы линейной структуры', 'lect3', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37011');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('4. Ветвления', 'lect45', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37014');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('5. Циклы', 'lect67', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37017');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('6. Одномерные массивы', 'lect812', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37024');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('7. Двумерные массивы', 'lect1315', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37030');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('8. Строки', 'lect1617', 'Лекция', " + R.drawable.n4 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('9. Указатели', 'lect21', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('10. Функции', 'lect22', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('11. Рекурсивные функции', 'lect23', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('12. Файлы', 'lect24', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('13. Структуры и объединения', 'lect25', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('14. Динамические типы данных', 'lect26', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('15. Контейнерные классы', 'lect27', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_DESC  + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE_ID + ", " + COLUMN_URL + ") VALUES ('16. Объектно-ориентированное программирование', 'lect28', 'Лекция', " + R.drawable.n1 + ", 'http://yagu.s-vfu.ru/mod/resource/view.php?id=37037');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}