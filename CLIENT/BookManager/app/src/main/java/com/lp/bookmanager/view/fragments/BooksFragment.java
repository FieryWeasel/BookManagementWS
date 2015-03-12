package com.lp.bookmanager.view.fragments;

import android.app.Activity;
import android.net.Uri;
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
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.tools.network.NetworkRequests;
import com.lp.bookmanager.view.adapters.ListBooksAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment implements NetworkRequests.BookListListener {

    private RecyclerView mRecyclerView;
    private boolean booksDownloaded;
    private List<Book> mListBooks;

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
        NetworkRequests.GetBookList(getActivity(), BooksFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        NetworkRequests.GetBookList(getActivity(), this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listBooks);

        if(booksDownloaded){
            fillUI();
        }


        return view;
    }

    private void fillUI() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new ListBooksAdapter(mListBooks);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_books, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    //    LISTENERS

    @Override
    public void onBookListRetrieved(String jsoBooks) {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        booksDownloaded = true;
        Gson gson = new Gson();
        mListBooks = gson.fromJson(jsoBooks, new TypeToken<List<Book>>(){}.getType());
        if(mRecyclerView != null)
            fillUI();

    }

    @Override
    public void onFailToRetrieveBookList() {

    }


}
