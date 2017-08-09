package com.toxxic.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class CourseCatalogActivity extends AppCompatActivity {

    public static final String ACTION = "com.toxxic.trainingapp.intent.action.COURSE_CATALOG" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_catalog);
    }
}
