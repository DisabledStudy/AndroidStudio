package com.example.sql_lite_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.support.v4.util.Pair;

import com.example.sql_lite_helper.IOOperations.SqlLiteCreatorUpdater;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.example.sql_lite_helper", appContext.getPackageName());
        Log.d("HI", "Test debug");
    }

    @Test
    public void main_test(){
        final String LOG_DB_TAG = "DB";
        final String TABLE_NAME = "mytable";
        TreeMap<String, ArrayList<Pair<String, String>>> tablesToCreate = new TreeMap<String, ArrayList<Pair<String, String>>>();
        ArrayList<Pair<String, String>> columnsAndType = new ArrayList<Pair<String, String>>();
        columnsAndType.add(new Pair<>("Country","text"));
        columnsAndType.add(new Pair<>("Rating","text"));
        tablesToCreate.put(TABLE_NAME, columnsAndType);


        SqlLiteCreatorUpdater sqlDatabase = new SqlLiteCreatorUpdater(InstrumentationRegistry.getContext(), "myDb", tablesToCreate);
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
