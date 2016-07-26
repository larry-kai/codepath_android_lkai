package com.codepath.larry_kai.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by larry_kai on 7/26/16.
 */
public class ItemArrayAdapter extends ArrayAdapter<ToDoItem> {
    public ItemArrayAdapter(Context context, ArrayList<ToDoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);

        // Populate the data into the template view using the data object
        tvDesc.setText(item.description);

        // Return the completed view to render on screen
        return convertView;
    }
}
