package com.company;


import com.mongodb.*;

import javax.sound.midi.Soundbank;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {
    // mongodb connection
    private DBCollection userCollection;
    private DBCollection todoCollection;

    Scanner scanner = new Scanner(System.in);

      public App() throws UnknownHostException {
        // start connection, create DB, create Collections
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("javaTodo");
        database.createCollection("user", null);
        database.createCollection("todo", null);
        this.userCollection = database.getCollection("user");
        this.todoCollection = database.getCollection("todo");
        while (true) {
            LoginMenu();
        }
    }

    public void LoginMenu(){
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        int pick = 0;
        do {
            System.out.print("Type 1 or 2: ");
            pick = userIntInput();
        }
        while (pick != 1 && pick != 2);

        switch (pick){
            case 1:
                loginUser();
                break;
            case 2:
                createUser();
                break;
            default:
                break;
        }
    }

    public void createUser(){
        String username = "";
        String password = "";
        System.out.println("Pick a username");
        username = userStringIput();
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBCursor cursor = userCollection.find(query);
        if (cursor.hasNext()){
            System.out.println("This username already exists. Pick a different one");
        }
        else {
            System.out.println("Pick a password");
            password = userStringIput();
            User user = new User(username, password);
            userCollection.insert(user.getUser());
            System.out.println("Succesfully Signed Up!");
        }
    }

    public Boolean loginUser(){
        boolean loggedIn = false;
        String username = "";
        String password = "";

        System.out.println("Username: ");
        username = userStringIput();
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBCursor cursor = userCollection.find(query);

        if (!cursor.hasNext()) {
            System.out.println("Username not found in our Database");
        } else {
            System.out.println("Password: ");
            password = userStringIput();
            if (Integer.parseInt(password) == Integer.parseInt(cursor.next().get("password").toString())){
                System.out.println("Password is correct! You are now logged in");
                loggedIn = true;
            }
            else {
                System.out.println("Wrong Password");
            }
        }
        return loggedIn;
    }

    public void printMainMenu(){
        System.out.println("1. See Todos");
        System.out.println("2. Add Todos");
        System.out.println("3. Delete Todo");
        System.out.println("4. Logout");
        System.out.println("5. Logout and Quit");
    }

    public int userIntInput(){
        int input = 0;
        if (scanner.hasNextInt()){
            input = scanner.nextInt();
            scanner.nextLine();
        }
        return input;
    }

    public String userStringIput(){
        String str = "";
        if (scanner.hasNext()){
            str = scanner.next();
            scanner.nextLine();
        }
        return str;
    }


}
