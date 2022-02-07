package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.struct.book.BookEntity;

import java.util.Comparator;

public class BookComparator implements Comparator<BookEntity> {
    @Override
    public int compare(BookEntity o1, BookEntity o2) {
        return o1.getMostPopular().compareTo(o2.getMostPopular());
    }
}
