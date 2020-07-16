package com.company;


import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    // mongodb connection
    private DBCollection userCollection;
    private DBCollection todoCollection;

    public App() throws UnknownHostException {
        // start connection, create DB, create Collections
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("javaTodo");
        database.createCollection("user", null);
        database.createCollection("todo", null);
        this.userCollection = database.getCollection("user");
        this.todoCollection = database.getCollection("todo");

        while (true) {
            loginMenu();
        }

    }

    public void loginMenu() {
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        int pick = 0;
        do {
            System.out.print("Type 1 or 2: ");
            pick = userIntInput();
        }
        while (pick != 1 && pick != 2);

        switch (pick) {
            case 1:
                BasicDBObject user = loginUser();
                if (user == null) {
                    loginUser();
                }
                while (true) {
                    mainMenu(user);
                }
            case 2:
                createUser();
                break;
            default:
                break;
        }
    }

    public void createUser() {
        String username = "";
        String password = "";
        System.out.println("Pick a username");
        username = userStringIput();
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBCursor cursor = userCollection.find(query);
        if (cursor.hasNext()) {
            System.out.println("This username already exists. Pick a different one");
            createUser();
        } else {
            System.out.println("Pick a password");
            password = userStringIput();
            User user = new User(username, password);
            userCollection.insert(user.getUser());
            System.out.println("Succesfully Signed Up!");
        }
    }

    public BasicDBObject loginUser() {
        String username = "";
        String password = "";

        System.out.println("Username: ");
        username = userStringIput();
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        DBCursor cursor = userCollection.find(query);
        BasicDBObject user = null;

        if (!cursor.hasNext()) {
            System.out.println("Username not found in our Database");
        } else {
            System.out.println("Password: ");
            password = userStringIput();
            user = (BasicDBObject) cursor.next();

            if (password.equals(user.get("password").toString())) {
                System.out.println("Password is correct! You are now logged in");
            } else {
                System.out.println("Wrong Password");
                user = null;
            }
        }
        return user;
    }

    public void mainMenu(BasicDBObject user) {
        int pick;
        System.out.println("1. See Todos");
        System.out.println("2. Add Todos");
        System.out.println("3. Delete Todo");
        System.out.println("4. Logout");
        System.out.println("5. Logout and Quit");

        do {
            System.out.print("Type 1,2,3,4 or 5: ");
            pick = userIntInput();
        }
        while (pick != 1 && pick != 2 && pick != 3 && pick != 4 && pick != 5);

        switch (pick) {
            case 1:
                printTodo(user);
                break;
            case 2:
                insertTodo(user);
                break;
            case 3:
                deleteTodo(user);
                break;
            case 4:
                loginMenu();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public int userIntInput() {
        int input = 0;
        if (scanner.hasNextInt()) {
            input = scanner.nextInt();
            scanner.nextLine();
        }
        return input;
    }

    public String userStringIput() {
        String str = "";
        if (scanner.hasNext()) {
            str = scanner.next();
            scanner.nextLine();
        }
        return str;
    }

    public void printTodo(BasicDBObject user) {
        BasicDBObject query = new BasicDBObject();
        query.put("user_id", user.get("_id").toString());
        DBCursor cursor = todoCollection.find(query);
        System.out.println("====TODOS LIST====");
        for (int i = 1; cursor.hasNext(); i++) {
            System.out.println(i + ". " + cursor.next().get("todo").toString());
        }
        System.out.println("====END OF LIST====");
    }

    public void insertTodo(BasicDBObject user) {
        System.out.print("Type todo: ");
        String item = userStringIput();
        if (item != null) {
            Todo todo = new Todo(item, user.get("_id").toString());
            todoCollection.insert(todo.getTodo());
            System.out.println("Item added Successfully");
        } else {
            System.out.println("!!! Cannot be an empty string");
        }
    }

    public void deleteTodo(BasicDBObject user) {
        int index;
        int length;

        BasicDBObject query = new BasicDBObject();
        query.put("user_id", user.get("_id").toString());
        DBCursor cursor = todoCollection.find(query);
        length = cursor.count();
        String[] todolist = new String[length];
        for (int i = 0; i < length; i++){
            todolist[i] = cursor.next().get("todo").toString();
        }

        System.out.print("Which item do you want deleted: ");
        index = userIntInput();
        if (index - 1 >= 0 && index - 1 < length){
            query.put("todo", todolist[index-1]);
            todoCollection.findAndRemove(query);
            System.out.println("Item successfully removed!");
        }
        else{
            System.out.println("Wrong Input");
        }
    }
}


