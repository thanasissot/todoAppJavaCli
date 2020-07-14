package com.company;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Todo {
    private DBObject todo;

    public Todo(String item, String user_id){
        todo = new BasicDBObject();
        todo.put("todo", item);
        todo.put("user_id", user_id);
    }

    public DBObject getTodo() {
        return todo;
    }
}
