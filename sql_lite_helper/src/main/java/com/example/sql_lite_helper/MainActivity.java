package com.example.sql_lite_helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.example.database.R;
import com.example.sql_lite_helper.IOOperations.SqlLiteHelper;

public class MainActivity extends AppCompatActivity {
    final String LOG_DB_TAG = "DB";
    final String TABLE_NAME = "mytable";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SqlLiteHelper sqlDatabase = new SqlLiteHelper(this);
        SQLiteDatabase db = sqlDatabase.getWritableDatabase();
        Log.d(LOG_DB_TAG, "Data base prepared");

        //insert in table
        {
            Pair<String, String> pair = new Pair<String, String>("Russia", "Best");

            ContentValues cv = new ContentValues();
            cv.put("Country", pair.first);
            cv.put("Rating", pair.second);
            long rowId = db.insert(TABLE_NAME, null, cv);
            Log.d(LOG_DB_TAG, "Insert 'Country':" + pair.first + " And 'Rating:'" + pair.second + " with id:" + rowId);
        }

        //insert in table
        {
            Pair<String, String> pair = new Pair<String, String>("Egypt", "Good");

            ContentValues cv = new ContentValues();
            cv.put("Country", pair.first);
            cv.put("Rating", pair.second);
            long rowId = db.insert(TABLE_NAME, null, cv);
            Log.d(LOG_DB_TAG, "Insert 'Country':" + pair.first + " And 'Rating:'" + pair.second + " with id:" + rowId);
        }

        //read from query
        {
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int countryIdx = c.getColumnIndex("Country");
                    int ratingIdx = c.getColumnIndex("Rating");
                    Log.d(LOG_DB_TAG, "read 'Country':" + c.getString(countryIdx) + " And 'Rating:'" + c.getString(ratingIdx));
                } while (c.moveToNext());
            }
        }

        //update
        {
            Pair<String, String> pair = new Pair<String, String>("Egypt", "Ok");
            ContentValues cv = new ContentValues();
            cv.put("Country", pair.first);
            cv.put("Rating", pair.second);
            int updateCount = db.update(TABLE_NAME, cv,"Country = ?", new String[]{pair.first});
            Log.d(LOG_DB_TAG, "Insert 'Country':" + pair.first + " And 'Rating:'" + pair.second + " with updateCount:" + updateCount);
        }

        //read from query
        {
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int countryIdx = c.getColumnIndex("Country");
                    int ratingIdx = c.getColumnIndex("Rating");
                    Log.d(LOG_DB_TAG, "read 'Country':" + c.getString(countryIdx) + " And 'Rating:'" + c.getString(ratingIdx));
                } while (c.moveToNext());
            }
        }

        //delete table
        {
            int delCount = db.delete(TABLE_NAME, "", null);
            Log.d(LOG_DB_TAG, "deleted rows count = " + delCount);
        }
    }
}
