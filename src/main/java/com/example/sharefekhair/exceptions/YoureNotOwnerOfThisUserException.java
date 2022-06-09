package com.example.sharefekhair.exceptions;

public class YoureNotOwnerOfThisUserException extends IllegalArgumentException{
    public YoureNotOwnerOfThisUserException(String s) {
        super(s);
    }
}
