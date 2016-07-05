package com.codepath.larry_kai.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String itemText = getIntent().getStringExtra("itemText");
        EditText etItemText = (EditText) findViewById(R.id.etItemText);
        etItemText.setText(itemText);
        etItemText.setSelection(itemText.length());
    }

    public void onSave(View v) {
        Intent data = new Intent();
        EditText etItemText = (EditText) findViewById(R.id.etItemText);
        String itemText = etItemText.getText().toString();
        data.putExtra("itemText", itemText);
        data.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(RESULT_OK, data);
        this.finish();
    }
}
