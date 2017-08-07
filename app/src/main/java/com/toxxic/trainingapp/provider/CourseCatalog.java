package com.toxxic.trainingapp.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.UserDictionary;

/**
 * Created by smcaton on 8/4/2017.
 */

public final class CourseCatalog {
    public static final String AUTHORITY = "com.toxxic.trainingapp.provider";
    public static final String CONTENT_TYPE_PREFIX = "vnd.toxxic.";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    /**
     * Class CourseCatalog.Course
     */
    public static final class Course implements BaseColumns {

        /* INSERT INTO courses(title,author,descrlong,category,thumb_id) */
        public static final String TABLE = "courses";
        public static final String _ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String DESCRLONG = "descrlong";
        public static final String AUTHOR = "author";
        public static final String CATEGORY = "category";
        public static final String THUMB_ID = "thumb_id";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of words.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_TYPE_PREFIX + TABLE;

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single word.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_TYPE_PREFIX + TABLE;

        public static final String[] DEFAULT_PROJECTION =
                {
                        CourseCatalog.Course._ID,
                        CourseCatalog.Course.TITLE,
                        CourseCatalog.Course.DESCRLONG,
                        CourseCatalog.Course.AUTHOR,
                        CourseCatalog.Course.CATEGORY,
                        CourseCatalog.Course.THUMB_ID,
                };

        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        public static final String DEFAULT_SORT_ORDER = TITLE + " ASC";

        private int id;
        private String title;
        private String description;
        private String author;
        private String category;
        private int thumbnailId;

        public Course() {
            this(-1);

            String id = UserDictionary.Words._ID;
        }

        public Course(int id) {
            this(id, "");
        }

        public Course(int id, String title) {
            this(id, title, "");
        }

        public Course(int id, String title, String description) {
            this(id, title, description, "", "", -1);
        }

        public Course(int id, String title, String description, String author, String category, int thumbnailId) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.author = author;
            this.category = category;
            this.thumbnailId = thumbnailId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getThumbnailId() {
            return thumbnailId;
        }

        public void setThumbnailId(int thumbnailId) {
            this.thumbnailId = thumbnailId;
        }

        public static Course createInstanceFromCursor(Cursor cursor)
        {
            Course course = new Course();
            course.setId( cursor.getInt(cursor.getColumnIndexOrThrow(Course._ID))  );
            course.setTitle( cursor.getString(cursor.getColumnIndexOrThrow(Course.TITLE)) );
            course.setDescription( cursor.getString(cursor.getColumnIndexOrThrow(Course.DESCRLONG)) );
            course.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(Course.AUTHOR)));
            course.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(Course.CATEGORY)));
            course.setThumbnailId(cursor.getInt(cursor.getColumnIndexOrThrow(Course.THUMB_ID)));

            return course;
        }
    }


    public static final class Lesson implements BaseColumns  {
        public static final String TABLE = "lessons";
        public static final String _ID = BaseColumns._ID;
        public static final String COURSE_ID = "courseId";
        public static final String TITLE = "title";
        public static final String DESCRLONG = "descrlong";
        public static final String THUMB_ID = "thumb_id";
        public static final String VIDEO_TYPE = "video_type";
        public static final String VIDEO_ID = "video_id";
        public static final String SEQNUM = "seqnum";

        public static final String[] DEFAULT_PROJECTION =
                {
                        CourseCatalog.Lesson._ID, //"_id";
                        CourseCatalog.Lesson.COURSE_ID, //"courseId";
                        CourseCatalog.Lesson.SEQNUM, //"courseId";
                        CourseCatalog.Lesson.TITLE, //"title" ;
                        CourseCatalog.Lesson.DESCRLONG, //"descrlong" ;
                        CourseCatalog.Lesson.THUMB_ID, //"thumb_id" ;
                        CourseCatalog.Lesson.VIDEO_TYPE, //"video_type" ;
                        CourseCatalog.Lesson.VIDEO_ID  //"video_id" ;
                };
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLE);

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of words.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_TYPE_PREFIX + TABLE;

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single word.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_TYPE_PREFIX + TABLE;
        public static final String DEFAULT_SORT_ORDER = TITLE + " ASC";

        public static Lesson createInstanceFromCursor(Cursor cursor)
        {
            Lesson course = new Lesson();
            course.setId( cursor.getInt(cursor.getColumnIndexOrThrow(Lesson._ID))  );
            course.setCourseId( cursor.getInt(cursor.getColumnIndexOrThrow(Lesson.COURSE_ID))  );
            course.setTitle( cursor.getString(cursor.getColumnIndexOrThrow(Lesson.TITLE)) );
            course.setThumbId(cursor.getInt(cursor.getColumnIndexOrThrow(Lesson.THUMB_ID)) );
            course.setVideoId(cursor.getInt(cursor.getColumnIndexOrThrow(Lesson.VIDEO_ID)) );
            course.setVideoType(cursor.getString(cursor.getColumnIndexOrThrow(Lesson.VIDEO_TYPE)) );
            course.setSeqNum(cursor.getInt(cursor.getColumnIndexOrThrow(Lesson.SEQNUM)));
            return course;
        }
        public static String getCreateSQL()
        {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS ");
            sb.append(TABLE);
            sb.append("(");
            sb.append(Lesson._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT ");
            sb.append(Lesson.TITLE).append(" TEXT ").append(",");
            sb.append(Lesson.TITLE).append(" TEXT ").append(",");
            sb.append(Lesson.TITLE).append(" TEXT ").append(",");
            sb.append(Lesson.TITLE).append(" TEXT ");

            /*
                courseId INTEGER,
                title TEXT,
                descrlong TEXT,
                thumb_id INTEGER,
                video_type TEXT,
                video_id TEXT
                */
            sb.append(")");

            return sb.toString();
        }

        private int id;
        private int courseId;
        private String title;
        private String descrlong;
        private int thumbId;
        private String videoType;
        private int videoId;
        private int seqNum;

        public Lesson() {
            this(-1, -1, "", "", -1, "", -1);
        }

        public Lesson(int id, int courseId, String title, String descrlong, int thumbId, String videoType, int videoId) {
            this.id = id;
            this.courseId = courseId;
            this.title = title;
            this.descrlong = descrlong;
            this.thumbId = thumbId;
            this.videoType = videoType;
            this.videoId = videoId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescrlong() {
            return descrlong;
        }

        public void setDescrlong(String descrlong) {
            this.descrlong = descrlong;
        }

        public int getThumbId() {
            return thumbId;
        }

        public void setThumbId(int thumbId) {
            this.thumbId = thumbId;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public int getSeqNum() {
            return seqNum;
        }

        public void setSeqNum(int seqNum) {
            this.seqNum = seqNum;
        }
    }
}
