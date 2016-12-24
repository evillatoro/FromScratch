package com.edwinvillatoro.fromscratch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.edwinvillatoro.fromscratch.databinding.ActivityCategoryBinding;
import com.edwinvillatoro.fromscratch.model.database.SampleDBContract;
import com.edwinvillatoro.fromscratch.model.database.SampleDBSQLiteHelper;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";
    private ActivityCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

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
        values.put(SampleDBContract.Category.COLUMN_NAME, binding.categoryNameEditText.getText().toString());

        long newRowId = database.insert(SampleDBContract.Category.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    private void readFromDB() {
        String name = binding.categoryNameEditText.getText().toString();

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {
                SampleDBContract.Category._ID,
                SampleDBContract.Category.COLUMN_NAME,
        };

        String selection =
                SampleDBContract.Category.COLUMN_NAME + " like ?";

        String[] selectionArgs = {"%" + name + "%"};

        Cursor cursor = database.query(
                SampleDBContract.Category.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        binding.recycleView.setAdapter(new SampleRecyclerViewCursorAdapter(this, cursor));
    }
}
