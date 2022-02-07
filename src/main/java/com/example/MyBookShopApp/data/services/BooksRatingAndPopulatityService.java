package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.BookCrudRepository;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Api(description = "service for calculate popular")
public class BooksRatingAndPopulatityService {

    private BookRepository bookRepository;
    private BookCrudRepository bookCrudRepository;

    @Autowired
    public BooksRatingAndPopulatityService(BookRepository bookRepository, BookCrudRepository bookCrudRepository) {
        this.bookRepository = bookRepository;
        this.bookCrudRepository = bookCrudRepository;
    }

    public void calculatePopularBooks(){
        List<BookEntity> bookRepositoryAll = bookRepository.findAll();
        for (BookEntity book : bookRepositoryAll) {
            calculate(book);
        }
    }

    private void calculate(BookEntity book){
        Double calculate = book.getUsersBuyThisBook() + 0.7 * book.getUsersHaveThisBookInCart() + 4.0 * book.getUsersHowPostponedBook();
        insertPopular(book, calculate);
    }

    private void insertPopular(BookEntity book, Double calculate){
        BookEntity bookPopular = bookCrudRepository.findById(book.getId()).get();
        bookPopular.setMostPopular(calculate);
        bookCrudRepository.save(bookPopular);
    }
}
