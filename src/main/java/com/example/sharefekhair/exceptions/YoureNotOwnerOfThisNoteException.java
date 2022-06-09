package com.example.sharefekhair.exceptions;

public class YoureNotOwnerOfThisNoteException extends IllegalArgumentException{
    public YoureNotOwnerOfThisNoteException(String s) {
        super(s);
    }
}
