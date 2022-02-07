package com.example.MyBookShopApp.errs;

public class BookstoreApiWrongParameterException extends Throwable {
    public BookstoreApiWrongParameterException(String message) {
        super(message);
    }
}
