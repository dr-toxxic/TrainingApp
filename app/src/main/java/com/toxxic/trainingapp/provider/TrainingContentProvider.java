package com.toxxic.trainingapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.toxxic.trainingapp.R;


public class TrainingContentProvider extends ContentProvider {

    private static final String DBNAME = "traning_db";          // Defines the database name
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH); // Creates a UriMatcher object.

    static {
        sUriMatcher.addURI(CourseCatalog.AUTHORITY, CourseCatalog.Course.TABLE, 1);
        sUriMatcher.addURI(CourseCatalog.AUTHORITY, CourseCatalog.Course.TABLE + "/#", 2);
        sUriMatcher.addURI(CourseCatalog.AUTHORITY, CourseCatalog.Lesson.TABLE, 3);
        sUriMatcher.addURI(CourseCatalog.AUTHORITY, CourseCatalog.Lesson.TABLE + "/#", 4);
    }

    private MainDatabaseHelper mOpenHelper; // Defines a handle to the database helper object. The MainDatabaseHelper class is defined below
    private SQLiteDatabase db; // Holds the database object

    /**
     * constructor
     */
    public TrainingContentProvider() {
        Log.d(this.getClass().getName(), "constructor");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Insert code here to determine which table to open, handle error-checking, and so forth
        Log.d(this.getClass().getName(), "**************** METHOD: delete ****************");
        /*
         * Gets a writeable database. This will trigger its creation if it doesn't already exist.
         *
         */
        db = mOpenHelper.getWritableDatabase();
        int ret = -1;
        db = mOpenHelper.getReadableDatabase();
        String table = getTable(uri);
        ret = db.delete(table, selection, selectionArgs);
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(this.getClass().getName(), "**************** METHOD: getType ****************");
        String type = "";
        int match = sUriMatcher.match(uri);
        switch (match) {
            case 1:
                type = CourseCatalog.Course.CONTENT_TYPE;
                break;
            case 2:
                type = CourseCatalog.Course.CONTENT_ITEM_TYPE;
                break;
            case 3:
                type = CourseCatalog.Lesson.CONTENT_TYPE;
                break;
            case 4:
                type = CourseCatalog.Lesson.CONTENT_ITEM_TYPE;
                break;
        }



        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Insert code here to determine which table to open, handle error-checking, and so forth
        Log.d(this.getClass().getName(), "**************** METHOD: insert ****************");
        /*
         * Gets a writeable database. This will trigger its creation if it doesn't already exist.
         *
         */
        db = mOpenHelper.getWritableDatabase();


        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        /*
         * Creates a new helper object. This method always returns quickly.
         * Notice that the database itself isn't created or opened
         * until SQLiteOpenHelper.getWritableDatabase is called
         */
        Log.d(this.getClass().getName(), "**************** METHOD: onCreate ****************");
        mOpenHelper = new MainDatabaseHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        Log.d(this.getClass().getName(), "**************** METHOD: query ****************");
        StringBuilder sb = new StringBuilder();
        sb.append("parameters: ").append("\n");
        sb.append("uri    - ").append(uri).append("\n");
        sb.append("projection    - ").append(projection).append("\n");
        sb.append("selection     - ").append(selection).append("\n");
        sb.append("selectionArgs - ").append(selectionArgs).append("\n");
        sb.append("sortOrder     - ").append(sortOrder).append("\n");

        Log.d(this.getClass().getName(), sb.toString());

        db = mOpenHelper.getReadableDatabase();
        String table = getTable(uri);
        Log.d(this.getClass().getName(), "table: " + table);
        c = db.query(table, projection, selection, selectionArgs, "", "", sortOrder);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(this.getClass().getName(), "**************** METHOD: update ****************");
        StringBuilder sb = new StringBuilder();
        sb.append("parameters: ").append("\n");
        sb.append("\t").append("uri    - ").append(uri).append("\n");
        sb.append("\t").append("values    - ").append(values).append("\n");
        sb.append("\t").append("selection     - ").append(selection).append("\n");
        sb.append("\t").append("selectionArgs - ").append(selectionArgs).append("\n");

        Log.d(this.getClass().getName(), sb.toString());

        int ret = -1;
        db = mOpenHelper.getReadableDatabase();
        String table = getTable(uri);
        Log.d(this.getClass().getName(), "table: " + table);
        ret = db.update(table, values, selection, selectionArgs);

        return ret;
    }

    private String getTable(Uri uri) {
        Log.d(getClass().getName().toString(), "***************** METHOD: getTable *****************");
        String table = "";
        int match = sUriMatcher.match(uri);
        Log.d(this.getClass().getName(), "match: " + match);
        switch (match) {
            case 1:
            case 2:
                table = CourseCatalog.Course.TABLE;
                break;
            case 3:
            case 4:
                table = CourseCatalog.Lesson.TABLE;
                break;
        }

        Log.d(getClass().getName().toString(), "table: " + table);
        return table;
    }

    /**
     * Helper class that actually creates and manages the provider's underlying data repository.
     */
    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        /*
         * Instantiates an open helper for the provider's SQLite data repository
         * Do not do database creation and upgrade here.
         */
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
            this.context = context;
            Log.d(this.getClass().getName(), "**************** METHOD: constructor ****************");
        }

        /*
         * Creates the data repository. This is called when the provider attempts to open the
         * repository and SQLite reports that it doesn't exist.
         */
        public void onCreate(SQLiteDatabase db) {
            Log.d(this.getClass().getName(), "**************** METHOD: onCreate ****************");
            // Creates the main table
            Resources res = context.getResources();
            String[] stmts = res.getStringArray(R.array.sql_create_statements);
            for (int i = 0; i < stmts.length; i++) {
                String stmt = stmts[i];
                Log.d(this.getClass().getName(), stmt);
                db.execSQL(stmt);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(this.getClass().getName(), "**************** METHOD: onUpgrade ****************");
        }
    }
}
