package com.edwinvillatoro.fromscratch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edwinvillatoro.fromscratch.model.database.SampleDBContract;
import com.edwinvillatoro.fromscratch.model.database.SampleDBSQLiteHelper;

public class CourseActivity extends AppCompatActivity {

    private static final String TAG = "CourseActivity";

    private RecyclerView recyclerView;

    private EditText courceNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Button save = (Button) findViewById(R.id.saveButton);
        Button search = (Button) findViewById(R.id.searchButton);
        courceNameEditText = (EditText) findViewById(R.id.courseNameEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromDB();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void saveToDB() {
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SampleDBContract.Course.COLUMN_COURSE_NAME, courceNameEditText.getText().toString());

        long newRowId = database.insert(SampleDBContract.Course.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    private void readFromDB() {
        String name = courceNameEditText.getText().toString();

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {
                SampleDBContract.Course._ID,
                SampleDBContract.Course.COLUMN_COURSE_NAME,
        };

        String selection =
                SampleDBContract.Course.COLUMN_COURSE_NAME + " like ?";

        String[] selectionArgs = {"%" + name + "%"};

        Cursor cursor = database.query(
                SampleDBContract.Course.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        recyclerView.setAdapter(new CourseRecyclerViewCursorAdapter(this, cursor));
    }
}
