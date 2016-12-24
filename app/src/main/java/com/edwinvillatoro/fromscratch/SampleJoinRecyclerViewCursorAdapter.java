package com.edwinvillatoro.fromscratch;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwinvillatoro.fromscratch.databinding.AssignmentListItemBinding;
import com.edwinvillatoro.fromscratch.model.database.SampleDBContract;

public class SampleJoinRecyclerViewCursorAdapter extends RecyclerView.Adapter<SampleJoinRecyclerViewCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public SampleJoinRecyclerViewCursorAdapter(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AssignmentListItemBinding itemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bindCursor(Cursor cursor) {
            itemBinding.assignmentNameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Assignment.COLUMN_ASSIGNMENT_NAME)
            ));
            itemBinding.scoreLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Assignment.COLUMN_SCORE)
            ));
            itemBinding.categoryLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Category.COLUMN_NAME)
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
                R.layout.assignment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}