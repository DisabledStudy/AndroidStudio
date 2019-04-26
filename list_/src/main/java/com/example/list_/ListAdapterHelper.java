package com.example.list_;

import java.util.Map;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

public class ListAdapterHelper {

    Context ctx;

    ListAdapterHelper(Context _ctx) {
        ctx = _ctx;
    }

    private SimpleExpandableListAdapter adapter;


    SimpleExpandableListAdapter getAdapter(ListTreeDictionary dict) {

        adapter = new SimpleExpandableListAdapter(
                ctx,
                dict.getAdaptorGroups(),
                android.R.layout.simple_expandable_list_item_1,
                dict.tagGroupsToShow(),
                dict.getGroupIndexes(),
                dict.getAdaptorProducts(),
                android.R.layout.simple_list_item_1,
                dict.tagProductsToShow(),
                dict.getProductIndexes());
        return adapter;
    }

    String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get("groupName");
    }

    String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get("productName");
    }

    String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }
}