package com.edwinvillatoro.fromscratch;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwinvillatoro.fromscratch.databinding.CourseListItemBinding;
import com.edwinvillatoro.fromscratch.model.database.SampleDBContract;

/**
 * Created by edwinvillatoro on 12/26/16.
 */

public class CourseRecyclerViewCursorAdapter extends RecyclerView.Adapter<CourseRecyclerViewCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public CourseRecyclerViewCursorAdapter(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CourseListItemBinding itemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bindCursor(Cursor cursor) {
            itemBinding.courseNameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Course.COLUMN_COURSE_NAME)
            ));
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.course_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
