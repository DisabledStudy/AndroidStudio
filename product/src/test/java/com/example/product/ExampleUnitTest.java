package com.example.product;


import android.support.v4.util.Pair;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void product_gives_requirement() {
        Product product = new Product();
        product.category = "Молоко";
        product.brand = "Веселый молочник";
        product.price = 1.3f;

        ArrayList<Pair<String,String>> requirements = product.getRequirements();
        assertTrue(requirements.contains(new Pair<String, String>("category","Молоко")));
        assertTrue(requirements.contains(new Pair<String, String>("brand","Веселый молочник")));
        assertTrue(requirements.contains(new Pair<String, String>("price","1.3")));
    }
}