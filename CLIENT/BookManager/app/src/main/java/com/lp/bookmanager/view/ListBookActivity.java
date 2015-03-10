package com.lp.bookmanager.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.view.adapters.ListBooksAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListBookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.listBooks);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Book> listBooks = new ArrayList<Book>();
        listBooks.add(new Book("eqrger", "dflvdv", 2, 2, "gbgr"));
        listBooks.add(new Book("eqrethysrtyger", "tyj", 2, 2, "qe(y("));
        listBooks.add(new Book("(yy((y", "dflv(ey'(ydv", 2, 2, "gbe(ryegr"));
        listBooks.add(new Book("e'(y'(yqrger", "dflv'(y'(ydv", 2, 2, "gbg'(yr's:bds:bgekr:rthr:rth:rth"));

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new ListBooksAdapter(listBooks);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
