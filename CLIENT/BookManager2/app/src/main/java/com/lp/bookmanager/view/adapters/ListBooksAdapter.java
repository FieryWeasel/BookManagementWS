package com.lp.bookmanager.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.tools.DataManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iem on 05/12/14.
 */
public class ListBooksAdapter extends RecyclerView.Adapter<ListBooksAdapter.ViewHolder>{

    private Context context;
    private int resource;
    private LayoutInflater inflater;

    private List<Book> mDataset;
    private CardClickListener mListener;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mRoot;
        // each data item is just a string in this case
        ImageView cover;
        TextView title;
        TextView author;
        TextView sumary;

        public ViewHolder(View v) {
            super(v);
            mRoot = (CardView)v.findViewById(R.id.root_item_bookList);
            cover = (ImageView)v.findViewById(R.id.cover_item_bookList);
            title = (TextView)v.findViewById(R.id.title_item_bookList);
            author = (TextView)v.findViewById(R.id.author_item_bookList);
            sumary = (TextView)v.findViewById(R.id.summary_item_bookList);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListBooksAdapter(Context context, List<Book> myDataset, final CardClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListBooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listbooks, parent, false);
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
        Picasso.with(mContext).load(mDataset.get(position).getCover()).error(R.drawable.book).into(holder.cover);
//        holder.author.setText(mDataset.get(position).getAuthor_id());
        holder.author.setText(DataManager.getInstance().getAuthorFromId(mDataset.get(position).getAuthor_id()));
        holder.sumary.setText(mDataset.get(position).getSummary());
        holder.sumary.setMinLines(1);
        holder.sumary.setMaxLines(3);
        holder.sumary.setEllipsize(TextUtils.TruncateAt.END);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface CardClickListener{
        public void onCardClickListener(int position);
    }
}
