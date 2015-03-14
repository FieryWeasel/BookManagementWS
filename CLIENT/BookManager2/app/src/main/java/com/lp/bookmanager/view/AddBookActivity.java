package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Author;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.DataManager;
import com.lp.bookmanager.tools.network.NetworkRequests;

public class AddBookActivity extends Activity implements NetworkRequests.AuthorCreatedListener, NetworkRequests.BookrCreatedListener {

    private TextView mAuthorFN_add_book;
    private TextView mAuthorLN_add_book;
    private TextView mIsbn_add_book;
    private TextView mSummary_add_book;
    private TextView mTitle_add_book;
    private ProgressDialog mRingProgressDialog;
    private Author mAuthor;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mAuthorFN_add_book = (TextView) findViewById(R.id.authorFN_add_book);
        mAuthorLN_add_book = (TextView) findViewById(R.id.authorLN_add_book);
        mIsbn_add_book = (TextView) findViewById(R.id.isbn_add_book);
        mSummary_add_book = (TextView) findViewById(R.id.summary_add_book);
        mTitle_add_book = (TextView) findViewById(R.id.title_add_book);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_book) {
            if(verifyFields()) {
                mAuthor = new Author(mAuthorFN_add_book.getText().toString(), mAuthorLN_add_book.getText().toString());
                NetworkRequests.createAuthor(this, mAuthor, AddBookActivity.this);
                mRingProgressDialog = ProgressDialog.show(this, getString(R.string.wait), getString(R.string.loading), true);
                mRingProgressDialog.setCancelable(false);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean verifyFields() {
        boolean infosOk = true;

        if(mAuthorFN_add_book.getText() == null
                || mAuthorFN_add_book.getText().toString().equalsIgnoreCase("")){

            mAuthorFN_add_book.setError(getString(R.string.authorFN_eror));
            infosOk = false;
        }

        if(mAuthorLN_add_book.getText() == null
                || mAuthorLN_add_book.getText().toString().equalsIgnoreCase("")){

            mAuthorLN_add_book.setError(getString(R.string.authorLN_eror));

            infosOk = false;
        }

        if(mTitle_add_book.getText() == null
                || mTitle_add_book.getText().toString().equalsIgnoreCase("")){

            mTitle_add_book.setError(getString(R.string.title_error));
            infosOk = false;
        }

        if((mIsbn_add_book.getText() == null
                || mIsbn_add_book.getText().toString().equalsIgnoreCase("")
                || (mIsbn_add_book.getText().toString().replaceAll("[\\W]", "").length() != 10
                && mIsbn_add_book.getText().toString().replaceAll("[\\W]", "").length() != 13) ) ){

            mIsbn_add_book.setError(getString(R.string.isbn_error));
            infosOk = false;
        }

        if(mSummary_add_book.getText() == null
                || mSummary_add_book.getText().toString().equalsIgnoreCase("")){

            mSummary_add_book.setError(getString(R.string.summary_error));
            infosOk = false;
        }


        return infosOk;
    }

    @Override
    public void onAuthorCreatedSuccessful(String id) {

        DataManager.getInstance().getmListAuthor().add(mAuthor);

        mBook = new Book(mIsbn_add_book.getText().toString(),
                mTitle_add_book.getText().toString(),
                1,
                Integer.parseInt(id),
                mSummary_add_book.getText().toString());

        NetworkRequests.createBook(this, mBook, AddBookActivity.this);

    }

    @Override
    public void onAuthorCreatedFailed() {
        mRingProgressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Error, please try again");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onBookCreatedSuccessful() {
        mRingProgressDialog.dismiss();
        DataManager.getInstance().getmListBook().add(mBook);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBookCreatedFailed() {
        mRingProgressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Error, please try again");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
