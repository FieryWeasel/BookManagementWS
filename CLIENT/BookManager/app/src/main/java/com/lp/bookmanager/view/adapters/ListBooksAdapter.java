package com.lp.bookmanager.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.bookmanager.model.Book;

import java.util.zip.Inflater;

/**
 * Created by iem on 05/12/14.
 */
public class ListBooksAdapter extends ArrayAdapter<Book>{

    private Context context;
    private int resource;
    private LayoutInflater inflater;

    public ListBooksAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(resource, parent, false);

        }

        return super.getView(position, convertView, parent);
    }

    public class ViewHolder{
        ImageView icon;
        TextView title;
        TextView author;
        TextView isbn;
    }
}
