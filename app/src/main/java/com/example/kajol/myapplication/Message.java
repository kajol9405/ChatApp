package com.example.kajol.myapplication;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("username")
    String username;
    @SerializedName("message")
    String message;
    long timestamp;

    public Message(String username, String message, long timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}

