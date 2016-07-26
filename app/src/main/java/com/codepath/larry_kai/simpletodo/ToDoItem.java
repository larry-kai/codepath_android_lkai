package com.codepath.larry_kai.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;

/**
 * Created by larry_kai on 7/26/16.
 */
@Table(name = "ToDoItems")
public class ToDoItem extends Model {
    @Column(name = "Description")
    public String description;

    public ToDoItem() {
        super();
    }

    public ToDoItem(String description){
        super();
        this.description = description;
    }

    public static ArrayList<ToDoItem> getAll() {
        return new Select().from(ToDoItem.class).execute();
    }
}
