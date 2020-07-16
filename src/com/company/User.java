package com.company;

import com.mongodb.BasicDBObject;

public class User {
    private BasicDBObject user;

    public User(String username, String password){
        user = new BasicDBObject();
        user.put("username", username);
        user.put("password", password);
    }

    public BasicDBObject getUser() {
        return user;
    }

}
