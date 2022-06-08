package com.example.sharefekhair.exceptions;

public class SessionIdNotFoundException extends IllegalArgumentException{
    public SessionIdNotFoundException(String s) {
        super(s);
    }
}
