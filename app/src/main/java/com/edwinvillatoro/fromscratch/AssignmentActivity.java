package com.edwinvillatoro.fromscratch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.edwinvillatoro.fromscratch.databinding.ActivityAssignmentBinding;
import com.edwinvillatoro.fromscratch.model.database.SampleDBContract;
import com.edwinvillatoro.fromscratch.model.database.SampleDBSQLiteHelper;

public class AssignmentActivity extends AppCompatActivity {

    private ActivityAssignmentBinding binding;
    private static final String TAG = "AssignmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment);

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        String[] queryCols = new String[]{"_id", SampleDBContract.Category.COLUMN_CATEGORY_NAME};
        String[] adapterCols = new String[]{SampleDBContract.Category.COLUMN_CATEGORY_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        Cursor cursor = database.query(
                SampleDBContract.Category.TABLE_NAME,     // The table to query
                queryCols,                                // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(cursorAdapter);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromDB();
            }
        });
    }

    private void saveToDB() {
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SampleDBContract.Assignment.COLUMN_ASSIGNMENT_NAME, binding.assignmentNameEditText.getText().toString());
        values.put(SampleDBContract.Assignment.COLUMN_SCORE, Integer.parseInt(binding.scoreEditText.getText().toString()));
        values.put(SampleDBContract.Assignment.COLUMN_CATEGORY_ID,
                ((Cursor)binding.categorySpinner.getSelectedItem()).getInt(0));

        Log.d("getINT", ((Cursor)binding.categorySpinner.getSelectedItem()).getInt(0) + "");
        Log.d("getColumnName", ((Cursor)binding.categorySpinner.getSelectedItem()).getColumnName(0));

        long newRowId = database.insert(SampleDBContract.Assignment.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    private void readFromDB() {
        String firstname = binding.assignmentNameEditText.getText().toString();
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        try
        {
            int score = Integer.parseInt(binding.scoreEditText.getText().toString());


            String[] selectionArgs = {"%" + firstname + "%", "%" + score + "%"};

            Cursor cursor = database.rawQuery(SampleDBContract.SELECT_EMPLOYEE_WITH_EMPLOYER, selectionArgs);
            binding.recycleView.setAdapter(new SampleJoinRecyclerViewCursorAdapter(this, cursor));
        }
        catch(NumberFormatException e)
        {
            //If number is not integer,you wil get exception and exception message will be printed
            Log.d(TAG, e.getMessage());

            int categoryID = ((Cursor)binding.categorySpinner.getSelectedItem()).getInt(0);
            String[] selectionArgs = {"%" + categoryID + "%"};

            Cursor cursor = database.rawQuery(SampleDBContract.SELECT_EMPLOYEE_WITH_CATEGORY, selectionArgs);
            binding.recycleView.setAdapter(new SampleJoinRecyclerViewCursorAdapter(this, cursor));
        }

    }
}
