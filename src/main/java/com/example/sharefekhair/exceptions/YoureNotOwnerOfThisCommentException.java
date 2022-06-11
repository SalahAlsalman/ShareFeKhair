package com.example.sharefekhair.exceptions;

public class YoureNotOwnerOfThisCommentException extends IllegalArgumentException{
    public YoureNotOwnerOfThisCommentException(String s) {
        super(s);
    }
}
