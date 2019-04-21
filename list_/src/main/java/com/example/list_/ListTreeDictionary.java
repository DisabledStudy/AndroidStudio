package com.example.list_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTreeDictionary {
    private final String ATTR_GROUP_NAME= "groupName";
    private final String ATTR_PRODUCT_NAME = "productName";

    ListTreeDictionary(){
        groupIndexes_ = new int[] {android.R.id.text1};
        productIndexes_ = new int[] {android.R.id.text1};
        groupAndProducts_ = new HashMap<String, List<String>>();
    }

    public void add(String group, List<String> products){
        List<String> currentProducts = groupAndProducts_.get(group);
        if(currentProducts == null){
            groupAndProducts_.put(group, products);
        }
        else{
            currentProducts.addAll(products);
        }
    }
    public void add(String category){
        add(category, new ArrayList<String>());
    }
    public void add(String category, String product){
        ArrayList<String> products = new ArrayList<String>();
        products.add(product);
        add(category, products);
    }

    public List<String> getProducts(String category){
        return groupAndProducts_.get(category);
    }

    String [] tagGroupsToShow(){ return new String[] {ATTR_GROUP_NAME}; }
    int [] getGroupIndexes(){ return groupIndexes_; }
    String [] tagProductsToShow(){ return new String[] {ATTR_PRODUCT_NAME}; }
    int [] getProductIndexes(){ return productIndexes_; }

    private void parseToAdaptorCompatible(){
        adaptorGroups_ = new ArrayList<HashMap<String, String>> ();
        adaptorProducts_ = new ArrayList<ArrayList<Map<String, String>>>();
        for (HashMap.Entry<String,List<String>> entry : groupAndProducts_.entrySet())
        {
            String group = entry.getKey();
            List<String> products = entry.getValue();

            HashMap<String, String> tagAndGroup = new HashMap<String, String>();
            tagAndGroup.put(ATTR_GROUP_NAME, group);
            adaptorGroups_.add(tagAndGroup);

            ArrayList<Map<String, String>> allProductForGroup= new ArrayList<Map<String, String>>();

            for(String product: products){
                Map<String, String> tagAndProduct = new HashMap<String, String>();
                tagAndProduct.put(ATTR_PRODUCT_NAME, product);
                allProductForGroup.add(tagAndProduct);
            }

            adaptorProducts_.add(allProductForGroup);
        }
        parsed_ = true;
    }

    ArrayList<HashMap<String, String>> getAdaptorGroups(){
        if(!parsed_){
            parseToAdaptorCompatible();
        }
        return adaptorGroups_;
    };

    ArrayList<ArrayList<Map<String, String>>> getAdaptorProducts(){
        if(!parsed_){
            parseToAdaptorCompatible();
        }
        return adaptorProducts_;
    }

    private int [] groupIndexes_;
    private int [] productIndexes_;
    private HashMap<String, List<String>> groupAndProducts_;
    private ArrayList<HashMap<String, String>> adaptorGroups_;
    private ArrayList<ArrayList<Map<String, String>>> adaptorProducts_;
    private boolean parsed_ = false;

}
