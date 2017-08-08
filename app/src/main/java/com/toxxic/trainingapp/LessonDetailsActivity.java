package com.toxxic.trainingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.toxxic.trainingapp.provider.CourseCatalog;
import com.toxxic.trainingapp.util.ActivityHelper;

public class LessonDetailsActivity extends AppCompatActivity {
    public static final String ACTION = "com.toxxic.trainingapp.intent.action.LESSON_DETAILS";
    public static final String EXTRA_COURSE_ID = "com.toxxic.trainingapp.extra.COURSE_ID";
    public static final String EXTRA_LESSON_ID = "com.toxxic.trainingapp.extra.LESSON_ID";

    private int courseId;
    private int lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);
        Intent i = getIntent();
        if (i != null) {
            setCourseId(i.getIntExtra(LessonDetailsActivity.EXTRA_COURSE_ID, -1));
            setLessonId(i.getIntExtra(LessonDetailsActivity.EXTRA_LESSON_ID, -1));
        }

        CourseCatalog.Lesson l = getLesson();
        TextView tvCourseId = (TextView) findViewById(R.id.tvCourseId);
        TextView tvLessonId = (TextView) findViewById(R.id.tvLessonId);
        TextView tvLessonDescr = (TextView) findViewById(R.id.tvLessonDescr);
        TextView tvLessonTitle = (TextView) findViewById(R.id.tvLessonTitle);
        VideoView videoView = (VideoView) findViewById(R.id.vvLessonVideo);
        Button btnFullscreen = (Button) findViewById(R.id.btnFullscreen);

        tvCourseId.setText(String.valueOf(l.getCourseId()));
        tvLessonId.setText(String.valueOf(l.getId()));
        tvLessonTitle.setText(l.getTitle());
        tvLessonDescr.setText(l.getDescrlong());
        final String url = "http://www.ebookfrenzy.com/android_book/movie.mp4";
        videoView.setVideoPath(url);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        btnFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.showRemoteVideo(LessonDetailsActivity.this, url);
            }
        });

    }

    private CourseCatalog.Lesson getLesson() {
        Cursor c = getLessonCursor();
        CourseCatalog.Lesson l = null;
        if (c.moveToFirst()) {
            l = CourseCatalog.Lesson.createInstanceFromCursor(c);
        }
        return l;
    }

    private Cursor getLessonCursor() {
        String mStrCourseId = String.valueOf(LessonDetailsActivity.this.getCourseId());
        String mStrLessonId = String.valueOf(LessonDetailsActivity.this.getLessonId());
        // A "projection" defines the columns that will be returned for each row
        String[] mProjection =
                CourseCatalog.Lesson.DEFAULT_PROJECTION;

        // Defines a string to contain the selection clause
        StringBuilder sb = new StringBuilder();
        sb.append(CourseCatalog.Lesson.COURSE_ID).append(" = ? ");
        sb.append(" AND ");
        sb.append(CourseCatalog.Lesson._ID).append(" = ? ");

        String mSelectionClause = sb.toString();

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {mStrCourseId, mStrLessonId};
        String mSortOrder = "";
        // Does a query against the table and returns a Cursor object

        Cursor mCursor = getContentResolver().query(
                //Course.CONTENT_URI.buildUpon().appendPath( mStrCourseId).build() ,  // The content URI of the words table
                CourseCatalog.Lesson.CONTENT_URI,
                mProjection,                       // The columns to return for each row
                mSelectionClause,                   // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                mSortOrder);                       // The sort order for the returned rows
        return mCursor;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
