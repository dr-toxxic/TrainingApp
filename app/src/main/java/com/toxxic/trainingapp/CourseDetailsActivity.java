package com.toxxic.trainingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toxxic.trainingapp.provider.CourseCatalog;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final String ACTION = "com.toxxic.trainingapp.intent.action.COURSE_DETAILS";
    public static final String EXTRA_ID = "com.toxxic.trainingapp.extra.COURSE_ID";
    private int courseId;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getIntent().getIntExtra(CourseDetailsActivity.EXTRA_ID, -1);

        setContentView(R.layout.activity_course_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private Cursor getCourseCursor() {
        String mStrCourseId = String.valueOf(CourseDetailsActivity.this.getCourseId());

        // A "projection" defines the columns that will be returned for each row
        String[] mProjection =
                CourseCatalog.Course.DEFAULT_PROJECTION;

        // Defines a string to contain the selection clause
        String mSelectionClause = "_ID = ?";

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {mStrCourseId};
        String mSortOrder = "";
        // Does a query against the table and returns a Cursor object

        Cursor mCursor = getContentResolver().query(
                //Course.CONTENT_URI.buildUpon().appendPath( mStrCourseId).build() ,  // The content URI of the words table
                CourseCatalog.Course.CONTENT_URI,
                mProjection,                       // The columns to return for each row
                mSelectionClause,                   // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                mSortOrder);                       // The sort order for the returned rows
        return mCursor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
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

    /**
     * @return
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CourseDetailsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public CourseDetailsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CourseDetailsFragment newInstance(int sectionNumber, Cursor c) {
            CourseDetailsFragment fragment = new CourseDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            if (c != null) {
                /*
                 * Moves to the next row in the cursor. Before the first movement in the cursor, the
                 * "row pointer" is -1, and if you try to retrieve data at that position you will get an
                 * exception.
                 */
                if (c.moveToNext()) {
                    args.putString(CourseCatalog.Course.DESCRLONG, c.getString(c.getColumnIndex(CourseCatalog.Course.DESCRLONG)));
                    args.putString(CourseCatalog.Course.AUTHOR, c.getString(c.getColumnIndex(CourseCatalog.Course.AUTHOR)));
                }
            }

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course_details, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.tvCourseDescr);
            textView.setText(getArguments().getString(CourseCatalog.Course.DESCRLONG));

            textView = (TextView) rootView.findViewById(R.id.tvCourseAuthor);
            textView.setText(getArguments().getString(CourseCatalog.Course.AUTHOR));

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CourseLessonsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public CourseLessonsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CourseLessonsFragment newInstance(int courseId) {
            CourseLessonsFragment fragment = new CourseLessonsFragment();
            Bundle args = new Bundle();
            args.putInt(CourseDetailsActivity.EXTRA_ID, courseId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course_lessons, container, false);

            ListView list = (ListView) rootView.findViewById(R.id.lvLessons);
            Context ctxt = container.getContext();
            int courseId = getArguments().getInt(CourseDetailsActivity.EXTRA_ID);
            list.setAdapter(  new LessonListAdapter(ctxt,  getLessonsListCursor(ctxt, courseId ) ) );

            return rootView;
        }
        private Cursor getLessonsListCursor(Context context, int courseId)
        {
            // A "projection" defines the columns that will be returned for each row
            String[] mProjection = CourseCatalog.Lesson.DEFAULT_PROJECTION;

            // Defines a string to contain the selection clause
            String mSelectionClause = CourseCatalog.Lesson.COURSE_ID + " = ?";

            // Initializes an array to contain selection arguments
            String[] mSelectionArgs = {String.valueOf(courseId) };

            // Defines the sort order
            String mSortOrder = "";

            // Does a query against the table and returns a Cursor object
            Cursor mCursor = context.getContentResolver().query(
                    CourseCatalog.Lesson.CONTENT_URI,  // The content URI of the words table
                    mProjection,                       // The columns to return for each row
                    mSelectionClause,                   // Either null, or the word the user entered
                    mSelectionArgs,                    // Either empty, or the string the user entered
                    mSortOrder);                       // The sort order for the returned rows

            return mCursor;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            titles = CourseDetailsActivity.this.getResources().getStringArray(R.array.course_details_tabs);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a CourseDetailsFragment (defined as a static inner class below).
            Fragment f = null;
            switch (position) {
                case 0:
                    f = CourseDetailsFragment.newInstance(position + 1, CourseDetailsActivity.this.getCourseCursor());
                    break;
                case 1:
                    f = CourseLessonsFragment.newInstance(CourseDetailsActivity.this.getCourseId());
                    break;
                default:
                    f = CourseDetailsFragment.newInstance(position + 1, CourseDetailsActivity.this.getCourseCursor());
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position < titles.length && position >= 0)
                return titles[position];

            return "Section " + position;
        }
    }

    protected static final class LessonListAdapter extends CursorAdapter {
        public LessonListAdapter(Context context, Cursor c) {
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
            return LayoutInflater.from(context).inflate(R.layout.lesson_list_item, parent, false);
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
            TextView tvId = (TextView) view.findViewById(R.id.tvLessonId);
            TextView tvCourseId = (TextView) view.findViewById(R.id.tvCourseId);

            // Extract properties from cursor
            String id = String.valueOf( cursor.getInt(cursor.getColumnIndexOrThrow(CourseCatalog.Lesson._ID)) );
            String courseId = String.valueOf( cursor.getInt(cursor.getColumnIndexOrThrow(CourseCatalog.Lesson.COURSE_ID)) );
            String title = cursor.getString(cursor.getColumnIndexOrThrow(CourseCatalog.Lesson.TITLE));
            String descr = cursor.getString(cursor.getColumnIndexOrThrow(CourseCatalog.Lesson.DESCRLONG));


            // Populate fields with extracted properties
            tvId.setText(id);
            tvCourseId.setText(courseId);
            tvCourseTitle.setText(title);
            tvCourseDescr.setText(descr);
        }

    }
}
