package com.lp.bookmanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Book;

import java.util.List;

/**
 * Created by iem on 05/12/14.
 */
public class ListBooksAdapter extends RecyclerView.Adapter<ListBooksAdapter.ViewHolder>{

    private Context context;
    private int resource;
    private LayoutInflater inflater;

    private List<Book> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView icon;
        TextView title;
        TextView author;
        TextView sumary;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListBooksAdapter(List<Book> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListBooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // create a new view
        View v = inflater.inflate(R.layout.item_listbooks, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.title = (TextView)v.findViewById(R.id.title_item_bookList);
        vh.author = (TextView)v.findViewById(R.id.author_item_bookList);
        vh.sumary = (TextView)v.findViewById(R.id.summary_item_bookList);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset.get(position).getTitle());
//        holder.author.setText(mDataset.get(position).getAuthor_id());
        holder.author.setText("author");
        holder.sumary.setText(mDataset.get(position).getSummary());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
