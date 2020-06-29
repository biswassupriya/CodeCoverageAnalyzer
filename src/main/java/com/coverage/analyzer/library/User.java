package com.coverage.analyzer.library;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String userName;
    private int[] bookBorrowed = new int[6];
    private String[] messages = new String[2];
    private List<String> messageList = new ArrayList<>();

    public int setUserId(int id) {

        this.userId = id;
        return id;
    }

    public int getUserId(int i) {
        return userId;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

  //  public void setBookBorrowed(int bookBorrowed) {
     //   this.bookBorrowed = bookBorrowed;
  //  }

    public int[] getBookBorrowed() {
        return bookBorrowed;
    }

    public void setMessages(String[] message) {
        this.messages = message;
    }

    public String[] getMessages() {
        return messages;
    }

    public boolean appendMessage(String message) {
        int i = 0;
        do {
            if (messages[i] == null) {
                messages[i] = message;
                return true;
            }
            ++i;
        } while (messages.length - 1 == i);

        return false;
    }

    public String getAllMessagesByUserId(int userId) {
        if (this.userId == userId) {
           String msg = this.messages[0] == null ? "No Messages Found" : String.join(",", messages);
           return "User Id: " + userId + " Messages: " + msg;
        } else {
            return "User Id: " + userId + " not found";
        }
    }

  //  public int getBooksBorrowedCount(int userId) {
     //   if (this.userId == userId) {
            // return "UserId: Has borrowed books:".format(String.valueOf(userId), bookBorrowed.length;
       //} else {
          //  "User Id not found".format("", userId);
        //}

  //  }

}
