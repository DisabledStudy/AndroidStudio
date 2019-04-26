package com.example.sql_lite_helper.IOOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class SqlLiteCreatorUpdater extends SQLiteOpenHelper {
    public SqlLiteCreatorUpdater(Context context, String databaseName, Map<String, ArrayList<Pair<String, String>>> tablesToCreate){
        super(context, databaseName, null, 1);
        tablesToCreate_ = tablesToCreate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Map.Entry<String, ArrayList<Pair<String, String>>> entry : tablesToCreate_.entrySet())
        {
            String tableName = entry.getKey();
            ArrayList<Pair<String, String>> columnsAndType = entry.getValue();

            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("create table ").append(tableName).append(" (").
                    append("id integer primary key autoincrement");
            for (Pair<String, String> columnAndType: columnsAndType) {
                sqlQuery.append(",\n").append(columnAndType.first).append(" ").append(columnAndType.second);
            }
            sqlQuery.append("\n);");

            db.execSQL(sqlQuery.toString());
        }
        /*db.execSQL("create table " +tableName + " ("
                + "id integer primary key autoincrement,"
                + "Country text,"
                + "Rating text" + ");");
                */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private Map<String, ArrayList<Pair<String, String>>> tablesToCreate_;
}
