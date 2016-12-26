package com.edwinvillatoro.fromscratch.model.database;

import android.provider.BaseColumns;

import static com.edwinvillatoro.fromscratch.model.database.SampleDBContract.Assignment.COLUMN_ASSIGNMENT_NAME;
import static com.edwinvillatoro.fromscratch.model.database.SampleDBContract.Assignment.COLUMN_CATEGORY_ID;
import static com.edwinvillatoro.fromscratch.model.database.SampleDBContract.Assignment.COLUMN_SCORE;

public class SampleDBContract {

    public static final String SELECT_EMPLOYEE_WITH_EMPLOYER = "SELECT * " +
            "FROM " + Assignment.TABLE_NAME + " ee INNER JOIN " + Category.TABLE_NAME + " er " +
            "ON ee." + COLUMN_CATEGORY_ID + " = er." + Category._ID + " WHERE " +
            "ee." + COLUMN_ASSIGNMENT_NAME + " like ? AND ee." + COLUMN_SCORE + " like ?";

    public static final String SELECT_EMPLOYEE_WITH_CATEGORY = "SELECT * " +
            "FROM " + Assignment.TABLE_NAME + " ee INNER JOIN " + Category.TABLE_NAME + " er " +
            "ON ee." + COLUMN_CATEGORY_ID + " = er." + Category._ID + " WHERE " +
            "ee." + COLUMN_CATEGORY_ID + " like ?";

    private SampleDBContract() {

    }

    public static class Course implements BaseColumns {
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_COURSE_NAME = "courseName";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT " + ")";

    }

    // implements BaseColumns to have a _ID column
    // CREATE TABLE IF NOT EXISTS category (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, date INTEGER)
    public static class Category implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_CATEGORY_NAME = "categoryName";
        public static final String COLUMN_COURSE_ID = "course_id";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_COURSE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_COURSE_ID + ") REFERENCES " +
                Course.TABLE_NAME + "(" + Course._ID + ") " + ")";

    }

    public static class Assignment implements BaseColumns {
        public static final String TABLE_NAME = "assignment";
        public static final String COLUMN_ASSIGNMENT_NAME = "assignmentName";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_CATEGORY_ID = "category_id";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ASSIGNMENT_NAME + " TEXT, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES " +
                Category.TABLE_NAME + "(" + Category._ID + ") " + ")";

    }

}
