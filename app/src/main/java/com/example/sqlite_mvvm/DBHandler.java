package com.example.sqlite_mvvm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.sqlite_mvvm.model.UserData;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "user_db";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "user";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String AGE_COL = "age";

    public DBHandler(@Nullable Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }


    public void onCreate(@NonNull SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + AGE_COL + " TEXT)";
        db.execSQL(query);
    }

    public long addNewData(String str_name, String str_age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL,str_name);
        values.put(AGE_COL, str_age);

        long id = db.insert(TABLE_NAME, null, values);
        // db.close();
        return id;
    }

    public void updateData(String str_updateName, String str_updateAge,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, str_updateName);
        values.put(AGE_COL, str_updateAge);

        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        //db.close();
    }

    public MutableLiveData<String> deleteUserData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        MutableLiveData<String> liveData = new MutableLiveData<>();

        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        liveData.setValue("Delete Successfully");

        return liveData;
    }

    public MutableLiveData<List<UserData>> readData() {

        MutableLiveData<List<UserData>> liveData = new MutableLiveData<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        List<UserData> userList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                userList.add(new UserData(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getInt(0)));
            }
            while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        liveData.setValue(userList);

        return liveData;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
