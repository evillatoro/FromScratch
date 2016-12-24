package com.edwinvillatoro.fromscratch.model.database;

import android.provider.BaseColumns;

public class SampleDBContract {

    public static final String SELECT_EMPLOYEE_WITH_EMPLOYER = "SELECT * " +
            "FROM " + Assignment.TABLE_NAME + " ee INNER JOIN " + Category.TABLE_NAME + " er " +
            "ON ee." + Assignment.COLUMN_CATEGORY_ID + " = er." + Category._ID + " WHERE " +
            "ee." + Assignment.COLUMN_ASSIGNMENT_NAME + " like ? AND ee." + Assignment.COLUMN_SCORE + " like ?";

    public static final String SELECT_EMPLOYEE_WITH_CATEGORY = "SELECT * " +
            "FROM " + Assignment.TABLE_NAME + " ee INNER JOIN " + Category.TABLE_NAME + " er " +
            "ON ee." + Assignment.COLUMN_CATEGORY_ID + " = er." + Category._ID + " WHERE " +
            "ee." + Assignment.COLUMN_ASSIGNMENT_NAME + " like ?";

    private SampleDBContract() {

    }
    // implements BaseColumns to have a _ID column
    // CREATE TABLE IF NOT EXISTS category (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, date INTEGER)
    public static class Category implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT " + ")";
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
