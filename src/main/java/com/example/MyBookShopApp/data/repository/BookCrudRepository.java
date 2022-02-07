package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookCrudRepository extends CrudRepository<BookEntity, Integer> {
}
