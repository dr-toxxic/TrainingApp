package com.toxxic.trainingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toxxic.trainingapp.provider.CourseCatalog;
import com.toxxic.trainingapp.util.ActivityHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(  new CourseListAdapter(this, getCourseListCursor()) );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(this.getClass().getName(), "**************** METHOD: onItemClick ****************");
                Intent i = new Intent();
                i.setAction(CourseDetailsActivity.ACTION);
                TextView tvCourseId = (TextView)view.findViewById(R.id.tvCourseId);
                int courseId = Integer.parseInt((String)tvCourseId.getText());
                i.putExtra(CourseDetailsActivity.EXTRA_ID, courseId);
                i.setType("text/plain");

                startActivity(i);
            }
        });
    }

    private Cursor getCourseListCursor() {
        // A "projection" defines the columns that will be returned for each row
        String[] mProjection = CourseCatalog.Course.DEFAULT_PROJECTION;

        // Defines a string to contain the selection clause
        String mSelectionClause = null;

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {};
        String mSortOrder = "";
        // Does a query against the table and returns a Cursor object
        Cursor mCursor = getContentResolver().query(
                CourseCatalog.Course.CONTENT_URI,  // The content URI of the words table
                mProjection,                       // The columns to return for each row
                mSelectionClause,                   // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                mSortOrder);                       // The sort order for the returned rows
        return mCursor;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d(this.getClass().getName(), "item: " + item);

        if (id == R.id.nav_my_courses) {
            ActivityHelper.showMyCourses(this);
        } else if (id == R.id.nav_about) {
            ActivityHelper.showAbout(this);

        } else if (id == R.id.nav_catalog) {
            ActivityHelper.showCatalog( this);

        } else if (id == R.id.nav_help) {
            ActivityHelper.showHelp(this);

        } else if (id == R.id.nav_settings) {
            ActivityHelper.showSettings(this);
        } else if (id == R.id.nav_account) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected static final class CourseListAdapter extends CursorAdapter
    {
        public CourseListAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        /**
         * Makes a new view to hold the data pointed to by cursor.
         *
         * @param context Interface to application's global information
         * @param cursor  The cursor from which to get the data. The cursor is already
         *                moved to the correct position.
         * @param parent  The parent to which the new view is attached to
         * @return the newly created view.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.course_list_item, parent, false);
        }

        /**
         * Bind an existing view to the data pointed to by cursor
         *
         * @param view    Existing view, returned earlier by newView
         * @param context Interface to application's global information
         * @param cursor  The cursor from which to get the data. The cursor is already
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template

            TextView tvCourseTitle = (TextView) view.findViewById(R.id.tvCourseTitle);
            TextView tvCourseDescr = (TextView) view.findViewById(R.id.tvCourseDescr);
            //TextView tvId = (TextView) view.findViewById(R.id.tvCourseId);

            CourseCatalog.Course course = CourseCatalog.Course.createInstanceFromCursor(cursor);

            // Populate fields with extracted properties
            //tvId.setText(course.getId());
            tvCourseTitle.setText(course.getTitle());
            tvCourseDescr.setText(course.getDescription());
        }

    }

}
