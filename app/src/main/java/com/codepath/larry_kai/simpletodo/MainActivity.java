package com.codepath.larry_kai.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToDoItem> dbItems;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readItems();
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        ToDoItem item = new ToDoItem(itemText);
        item.save();
        dbItems.add(item);
        etNewItem.setText("");
    }

    private final int EDIT_ITEM_REQUEST = 20;

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                dbItems.get(position).delete();
                dbItems.remove(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("itemText", items.get(position));
                startActivityForResult(i, EDIT_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST) {
            String itemText = data.getExtras().getString("itemText");
            int position = data.getExtras().getInt("position");
            items.set(position, itemText);
            itemsAdapter.notifyDataSetChanged();
            dbItems.get(position).description = itemText;
            dbItems.get(position).save();
        }
    }

    private void readItems() {
        dbItems = ToDoItem.getAll();
        items = new ArrayList<String>();
        for (ToDoItem item : dbItems) {
            items.add(item.description);
        }
    }
}
