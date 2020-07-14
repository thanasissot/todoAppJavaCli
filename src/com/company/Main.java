package com.company;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
	// write your code here
        App todoApp =  new App();

//        // connect to database
//
//        Scanner scanner = new Scanner(System.in);
//
////        BasicDBObject user = new BasicDBObject();
////        user.put("name", "firstUser");
////        user.put("password", "123");
////        userCollection.insert(user);
//
//        BasicDBObject query = new BasicDBObject();
//        query.put("name", "firstUser");
//        DBCursor cursor = userCollection.find(query);
//
//        String user_id;
//        if (cursor.hasNext()){
//             user_id = cursor.next().get("_id").toString();
//             BasicDBObject todo =  new BasicDBObject();
//             todo.put("todo", "something");
//             todo.put("user_id", user_id);
//             todoCollection.insert(todo);
//        }

    }
}
