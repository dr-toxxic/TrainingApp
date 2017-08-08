package com.toxxic.trainingapp.util;

import android.content.Context;
import android.content.Intent;

import com.toxxic.trainingapp.CourseDetailsActivity;
import com.toxxic.trainingapp.FullscreenActivity;
import com.toxxic.trainingapp.LessonDetailsActivity;
import com.toxxic.trainingapp.VideoPlayerActivity;

/**
 * Created by smcaton on 8/8/2017.
 */

public class ActivityHelper {
    //Convenience method to show a video
    public static void showRemoteVideo(Context ctx, String url) {
        // Intent i = new Intent(ctx, VideoPlayerActivity.class);
        Intent i = new Intent();
        i.setAction(VideoPlayerActivity.ACTION);
        i.putExtra(VideoPlayerActivity.EXTRA_URL, url);
        i.setType("text/plain");
        ctx.startActivity(i);
    }

    public static void showCourse(Context ctx, int course_id) {
        Intent i = new Intent();
        i.setAction(CourseDetailsActivity.ACTION);
        i.putExtra(CourseDetailsActivity.EXTRA_ID, course_id);
        i.setType("text/plain");
        ctx.startActivity(i);
    }

    public static void showLesson(Context ctx, int course_id, int lesson_id) {
        Intent i = new Intent();
        i.setAction(LessonDetailsActivity.ACTION);
        i.putExtra(LessonDetailsActivity.EXTRA_COURSE_ID, course_id);
        i.putExtra(LessonDetailsActivity.EXTRA_LESSON_ID, lesson_id);
        i.setType("text/plain");
        ctx.startActivity(i);
    }
}
