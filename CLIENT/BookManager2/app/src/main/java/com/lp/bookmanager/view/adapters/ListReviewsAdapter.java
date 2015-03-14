package com.lp.bookmanager.view.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Review;
import com.lp.bookmanager.tools.DataManager;

import java.util.List;

/**
 * Created by Kazuya on 13/03/2015.
 * BookManager
 */
public class ListReviewsAdapter extends RecyclerView.Adapter<ListReviewsAdapter.ViewHolder> {

    private ReviewCardClickListener mListener;
    private List<Review> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mRoot;
        TextView author;
        TextView review;
        TextView title;
        TextView grade;

        public ViewHolder(View v) {
            super(v);
            mRoot = (CardView)v.findViewById(R.id.root_review);
            author = (TextView)v.findViewById(R.id.author_review_item);
            title = (TextView)v.findViewById(R.id.title_review_item);
            review = (TextView)v.findViewById(R.id.review_review_item);
            grade = (TextView)v.findViewById(R.id.grade_review_item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListReviewsAdapter(List<Review> myDataset, final ReviewCardClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listreviews, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCardClickListener(position);
            }
        });

        holder.title.setText(mDataset.get(position).getTitle());
        holder.author.setText("Written by " + DataManager.getInstance().getAuthorUserFromId(mDataset.get(position).getUser_id()));
        holder.grade.setText("Mark : "+ Integer.toString(mDataset.get(position).getMark())+"/10");
        holder.review.setText(mDataset.get(position).getComment());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ReviewCardClickListener{
        public void onCardClickListener(int position);
    }
}
