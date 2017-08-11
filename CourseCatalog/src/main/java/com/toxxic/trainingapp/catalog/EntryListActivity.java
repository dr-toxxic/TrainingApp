package com.toxxic.trainingapp.catalog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;



/**
 * Activity for holding EntryListFragment.
 */
public class EntryListActivity extends FragmentActivity {

    public static final String ACTION = "com.toxxic.trainingapp.catalog.intent.action.COURSE_CATALOG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);
    }
}
