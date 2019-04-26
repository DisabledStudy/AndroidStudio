package com.example.sql_lite_helper;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void main_test() {
        assertEquals(4, 2 + 2);
        String LOG_DB_TAG = "DB";
        Log.d(LOG_DB_TAG, "Test debug");
        LOG_DB_TAG = "1";
    }
}