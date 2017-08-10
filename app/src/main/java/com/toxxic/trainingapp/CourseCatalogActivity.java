package com.toxxic.trainingapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toxxic.trainingapp.fragment.CourseCatalogFragment;
import com.toxxic.trainingapp.fragment.dummy.CourseCatalogContent;


public class CourseCatalogActivity extends AppCompatActivity implements CourseCatalogFragment.OnListFragmentInteractionListener {

    public static final String ACTION = "com.toxxic.trainingapp.intent.action.COURSE_CATALOG" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_entry_list);

    }

    @Override
    public void onListFragmentInteraction(CourseCatalogContent.CatalogItem item) {

    }
}
