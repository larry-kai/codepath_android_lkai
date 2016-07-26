package com.codepath.larry_kai.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToDoItem> dbItems;
    ItemArrayAdapter itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbItems = ToDoItem.getAll();
        setContentView(R.layout.activity_main);
        itemsAdapter = new ItemArrayAdapter(this, dbItems);
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        ToDoItem item = new ToDoItem(etNewItem.getText().toString());
        item.save();
        itemsAdapter.add(item);
        etNewItem.setText("");
    }

    private final int EDIT_ITEM_REQUEST = 20;

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                ToDoItem item = dbItems.get(position);
                itemsAdapter.remove(item);
                item.delete();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("itemText", dbItems.get(position).description);
                startActivityForResult(i, EDIT_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST) {
            String itemText = data.getExtras().getString("itemText");
            int position = data.getExtras().getInt("position");
            dbItems.get(position).description = itemText;
            dbItems.get(position).save();
        }
    }
}
