package com.example.sashenkov_9.Help;

public interface LoginCallback {
    void onTokenRecieved(String error);
    void onError(String error);
}
