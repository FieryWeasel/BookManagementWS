package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.model.Review;
import com.lp.bookmanager.model.User;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.DataManager;
import com.lp.bookmanager.tools.network.NetworkRequests;
import com.lp.bookmanager.view.adapters.ListReviewsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookDetailsActivity extends Activity implements ListReviewsAdapter.ReviewCardClickListener, NetworkRequests.ReviewListListener, NetworkRequests.UserListListener {

    private Book mBook;
    private RecyclerView mRecyclerView;
    private ArrayList<Review> mListReviews;
    private ListReviewsAdapter mAdapter;
    private ProgressDialog mRingProgressDialog;
    private boolean mReviewsDownloaded;
    private boolean mUsersDownloaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        mBook = (Book)getIntent().getSerializableExtra("book");

        NetworkRequests.getReviewListById(this, mBook.getIsbn(), BookDetailsActivity.this);
        NetworkRequests.getUSerList(this, BookDetailsActivity.this);
        mRingProgressDialog = ProgressDialog.show(this, getString(R.string.wait), getString(R.string.loading), true);
        mRingProgressDialog.setCancelable(false);


        ImageView cover = (ImageView) findViewById(R.id.cover_book_detail);
        TextView summary = (TextView) findViewById(R.id.summary_book_detail);
        TextView title = (TextView) findViewById(R.id.title_book_detail);
        TextView author = (TextView) findViewById(R.id.author_book_detail);
        TextView isbn = (TextView) findViewById(R.id.isbn_book_detail);

        Picasso.with(this).load(mBook.getCover()).error(R.drawable.book).into(cover);
        title.setText(mBook.getTitle());
        summary.setText(mBook.getSummary());
        summary.setMinLines(1);
        summary.setMaxLines(4);
        summary.setMovementMethod(new ScrollingMovementMethod());
        author.setText(DataManager.getInstance().getAuthorFromId(mBook.getAuthor_id()));
        isbn.setText(mBook.getIsbn());

        mRecyclerView = (RecyclerView) findViewById(R.id.listReviews);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(mListReviews == null)
            mListReviews = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new ListReviewsAdapter(mListReviews, BookDetailsActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_review) {
            Intent intent = new Intent(BookDetailsActivity.this, AddReviewActivity.class);
            intent.putExtra("isbn", mBook.getIsbn());
            startActivityForResult(intent, Constants.ADD_REVIEW);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == Constants.ADD_REVIEW){
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCardClickListener(int position) {
        Intent intent = new Intent(BookDetailsActivity.this, ReviewDetailsActivity.class);
        intent.putExtra("review", mListReviews.get(position));
        startActivity(intent);
    }

    @Override
    public void onReviewListCorrectlyRetrieved(String jsonReview) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mReviewsDownloaded = true;
        Gson gson = new Gson();
        mListReviews = gson.fromJson(jsonReview, new TypeToken<List<Review>>(){}.getType());
        DataManager.getInstance().setmListReviews(mListReviews);
        if(mUsersDownloaded){
            mAdapter = new ListReviewsAdapter(mListReviews, BookDetailsActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }
    }

    @Override
    public void onFailToRetrieveReviews() {

        mReviewsDownloaded = true;
        mListReviews = new ArrayList<>();
        DataManager.getInstance().setmListReviews(mListReviews);
        if(mUsersDownloaded){
            mAdapter = new ListReviewsAdapter(mListReviews, BookDetailsActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }

    }

    @Override
    public void onUserListCorrectlyRetrieved(String jsonReview) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mUsersDownloaded = true;
        Gson gson = new Gson();
        List<User> mListUsers = gson.fromJson(jsonReview, new TypeToken<List<User>>() {
        }.getType());
        DataManager.getInstance().setmListUsers(mListUsers);
        if(mReviewsDownloaded){
            mAdapter = new ListReviewsAdapter(mListReviews, BookDetailsActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }
    }

    @Override
    public void onFailToRetrieveUsers() {
        mUsersDownloaded = true;
        List<User> mListUsers = new ArrayList<>();
        DataManager.getInstance().setmListUsers(mListUsers);
        if(mReviewsDownloaded){
            mAdapter = new ListReviewsAdapter(mListReviews, BookDetailsActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }
    }
}
