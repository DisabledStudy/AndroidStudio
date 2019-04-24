package com.example.product_getter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class ProductGetter extends SqlLiteHelper {
        ProductGetter(Context context) {
            super(context);
        }

        ArrayList<Float> getProductPrices(ArrayList<Product> products){
            return new ArrayList<Float>();
        }

        Boolean updateProduct(Product product){
            ArrayList<Product> oneProduct = new ArrayList<Product>();
            oneProduct.add(product);
            return updateProducts(oneProduct);
        }

        Boolean updateProducts(ArrayList<Product> products){
            return true;
        }

        Boolean loadProducts(ArrayList<Product> products){
            return true;
        }

    }
}
