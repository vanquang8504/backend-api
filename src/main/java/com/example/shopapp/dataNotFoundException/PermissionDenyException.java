package com.example.shopapp.dataNotFoundException;

public class PermissionDenyException extends Exception{
    public PermissionDenyException (String message){
        super(message);
    }
}
