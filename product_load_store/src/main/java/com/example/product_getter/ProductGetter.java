package com.example.product_getter;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.Pair;

import com.example.sql_lite_helper.IOOperations.SqlLiteCreatorUpdater;
import com.example.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class ProductGetter{
    private static final String DATABASE_NAME = "product_database";
    private final String TABLE_NAME = "product_table";
    ArrayList<Pair<String, String>> columnType;

    public ProductGetter(Context context) {
        columnType = new ArrayList<>();
        for(int i = 0; i < Product.names.size(); ++i){
            columnType.add(new Pair<>(Product.names.get(i), Product.types.get(i)));
        }

        TreeMap<String, ArrayList<Pair<String, String>>> tablesToCreate = new TreeMap<>();
        tablesToCreate.put(TABLE_NAME, columnType);
        SqlLiteCreatorUpdater sqlCreatorUpdater = new SqlLiteCreatorUpdater(context, DATABASE_NAME, tablesToCreate);
        database_ = sqlCreatorUpdater.getWritableDatabase();
    }

    public ArrayList<Product> loadProducts(Product productRequirement){
        ArrayList<Pair<String, Object>> requirementsColumns = productRequirement.columnsAndObject();

        StringBuilder sqlRawQuery = new StringBuilder("SELECT * \n FROM ").append(TABLE_NAME).append("\n");
        StringBuilder where = new StringBuilder();
        boolean whereExist = false;
        for (int i = 0; i < requirementsColumns.size(); i++) {
            Pair<String, Object> reqColValue = requirementsColumns.get(i);
            if(reqColValue.second != null){
                if(!whereExist){
                    where.append("WHERE ");
                    whereExist = true;
                }
                else{
                    where.append(" AND ");
                }
                where.append(reqColValue.first).append(" = ").append(reqColValue.second.toString());
            }
        }
        sqlRawQuery.append(where);

        ArrayList<Product> loadedProducts = new ArrayList<Product>();
        Cursor c = database_.rawQuery(sqlRawQuery.toString(), null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, Integer> indexVarible = new HashMap<>();
                for (String name : Product.names) {
                    int idx = c.getColumnIndex(name);
                    indexVarible.put(name, idx);
                }

                Product product = new Product();
                int idx = 0;
                product.category = c.getString(indexVarible.get(Product.names.get(idx++)));
                product.name = c.getString(indexVarible.get(Product.names.get(idx++)));
                product.brand = c.getString(indexVarible.get(Product.names.get(idx++)));
                product.weight = c.getFloat(indexVarible.get(Product.names.get(idx++)));
                product.price = c.getFloat(indexVarible.get(Product.names.get(idx)));

                loadedProducts.add(product);
            } while (c.moveToNext());
        }
        c.close();

        return  loadedProducts;
    }

    long saveProducts(ArrayList<Product> products){
        ContentValues contentValues = new ContentValues();
        for(Product product: products){
            ArrayList<Pair<String, Object>> columnsAndObject = product.columnsAndObject();
            for (int i = 0; i < columnsAndObject.size(); ++i){
                Pair<String, Object> columnAndObject = columnsAndObject.get(i);
                if(columnAndObject.second == null){
                    continue;
                }

                if(Product.types.get(i).equals("text")){
                    contentValues.put(columnAndObject.first, columnAndObject.second.toString());
                }
                else if(Product.types.get(i).equals("float")){
                    contentValues.put(columnAndObject.first, (Float) columnAndObject.second);
                }
                else{
                    throw new RuntimeException("unregistered type");
                }
            }
        }

        return database_.insertWithOnConflict(TABLE_NAME, null, contentValues, CONFLICT_REPLACE);
    }

    private SQLiteDatabase database_;
}
//public class ProductGetter extends DrawAlghoritms{
//}