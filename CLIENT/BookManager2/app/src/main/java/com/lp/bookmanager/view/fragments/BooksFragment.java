package com.lp.bookmanager.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Author;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.tools.Constants;
import com.lp.bookmanager.tools.DataManager;
import com.lp.bookmanager.tools.network.NetworkRequests;
import com.lp.bookmanager.view.AddBookActivity;
import com.lp.bookmanager.view.BookDetailsActivity;
import com.lp.bookmanager.view.adapters.ListBooksAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment implements NetworkRequests.BookListListener, NetworkRequests.AuthorListListener, ListBooksAdapter.CardClickListener {

    private RecyclerView mRecyclerView;
    private boolean booksDownloaded;
    private List<Book> mListBooks;
    private ListBooksAdapter mAdapter;
    private ProgressDialog mRingProgressDialog;
    private boolean authorsDownloaded;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BooksFragment.
     */
    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        NetworkRequests.getBookList(getActivity(), BooksFragment.this);
        NetworkRequests.getAuthorList(getActivity(), BooksFragment.this);
        mRingProgressDialog = ProgressDialog.show(getActivity(), getString(R.string.wait), getString(R.string.loading), true);
        mRingProgressDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        //NetworkRequests.getBookList(getActivity(), this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listBooks);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(mListBooks == null)
            mListBooks = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new ListBooksAdapter(getActivity(), mListBooks, BooksFragment.this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_books, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add_book :
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivityForResult(intent, Constants.ADD_BOOK);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.ADD_BOOK){
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*****************************************************************************/
    /**                                                                         **/
    /**                                LISTENERS                                **/
    /**                                                                         **/
    /*****************************************************************************/

    @Override
    public void onBookListRetrieved(String jsoBooks) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        booksDownloaded = true;
        Gson gson = new Gson();
        mListBooks = gson.fromJson(jsoBooks, new TypeToken<List<Book>>(){}.getType());
        DataManager.getInstance().setmListBook(mListBooks);
        if(authorsDownloaded){
            mAdapter = new ListBooksAdapter(getActivity(), mListBooks, BooksFragment.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }
    }


    @Override
    public void onFailToRetrieveBookList() {
        mRingProgressDialog.dismiss();
    }

    @Override
    public void onAuthorListCorrectlyRetrieved(String jsonAuthor) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        authorsDownloaded = true;
        Gson gson = new Gson();
        List<Author> mListAuthor = gson.fromJson(jsonAuthor, new TypeToken<List<Author>>() {
        }.getType());
        DataManager.getInstance().setmListAuthor(mListAuthor);
        if(booksDownloaded){
            mAdapter = new ListBooksAdapter(getActivity(), mListBooks, BooksFragment.this);
            mRecyclerView.setAdapter(mAdapter);
            mRingProgressDialog.dismiss();
        }
    }

    @Override
    public void onFailToRetrieveAuthorList() {

    }

    @Override
    public void onCardClickListener(int position) {
        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
        intent.putExtra("book", mListBooks.get(position));
        startActivity(intent);
    }
}
