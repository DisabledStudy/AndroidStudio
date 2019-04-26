package com.example.product;


import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    public String category;
    public String name;
    public String brand;
    public Float weight;
    public Float price;

    static public ArrayList<String> names = new ArrayList<>(Arrays.asList("category", "name", "brand", "weight", "price"));
    static public ArrayList<String> types = new ArrayList<>(Arrays.asList("text", "text", "text", "float", "float"));
    public ArrayList<Pair<String, Object>> columnsAndObject(){
        ArrayList<Pair<String, Object>> sqlVariables = new ArrayList<>();
        int index = 0;
        sqlVariables.add(new Pair<String, Object>(names.get(index++),category));
        sqlVariables.add(new Pair<String, Object>(names.get(index++),name));
        sqlVariables.add(new Pair<String, Object>(names.get(index++),brand));
        sqlVariables.add(new Pair<String, Object>(names.get(index++),weight));
        sqlVariables.add(new Pair<String, Object>(names.get(index++),price));

        return sqlVariables;
    }
}

