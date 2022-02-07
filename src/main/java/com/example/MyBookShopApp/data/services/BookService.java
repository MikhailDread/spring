package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.struct.author.AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private final BooksRatingAndPopulatityService booksRatingAndPopulatityService;

    @Autowired
    public BookService(BookRepository bookRepository, BooksRatingAndPopulatityService booksRatingAndPopulatityService) {
        this.bookRepository = bookRepository;
        this.booksRatingAndPopulatityService = booksRatingAndPopulatityService;
    }

    public Page<BookEntity> getBooksData(Integer offset, Integer size){
        Pageable nextPage = PageRequest.of(offset, size);
        return bookRepository.findAll(nextPage);
    }

    //NEW BOOK METHODS

    public List<BookEntity> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public List<BookEntity> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if(title.equals("") || title.length() <= 1){
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<BookEntity> data = bookRepository.findBooksByTitleContaining(title);
            if(data.size() > 0){
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("Not found data");
            }
        }
    }

    public List<BookEntity> getBookWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<BookEntity> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<BookEntity> getBooksWithMaxPrice(){
        return bookRepository.getBooksWithMAxDiscount();
    }

    public List<BookEntity> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<BookEntity> getBookCompilation(Integer offset, Integer size){
        Pageable nextPage = PageRequest.of(offset, size);
        return bookRepository.findAll(nextPage);
    }

    public Page<BookEntity> getBookPopularCompilation(Integer offset, Integer size){
        Pageable nextPage = PageRequest.of(offset, size);
        booksRatingAndPopulatityService.calculatePopularBooks();
        Page<BookEntity> all = bookRepository.findAll(nextPage);
        all.getContent().stream().sorted(new BookComparator()).close();
        return all;
    }

    public Page<BookEntity> getBookAuthorCompilation(Integer offset, Integer size, AuthorEntity author){
        Pageable nextPage = PageRequest.of(offset, size);
        return bookRepository.findBookEntityByAuthorId(nextPage, author.getId());
    }

    public Page<BookEntity> getBookRecentCompilation(Integer offset, Integer size){
        Pageable nextPage = PageRequest.of(offset, size);

        Date dateF = Date.valueOf(LocalDate.now().minusYears(3).toString());
        Date dateT = Date.valueOf(LocalDate.now().toString());
        return bookRepository.findBookEntityByPubDateBetween(dateF, dateT, nextPage);
    }

    public Page<BookEntity> getPageOfSearchResultBook(String searchWord, Integer offset, Integer limit){
        Pageable page = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, page);
    }

    public Page<BookEntity> getBookWithRecentAndData(String from, String to, Integer offset, Integer size) throws ParseException {
        Pageable nextPage = PageRequest.of(offset, size);
        if(from == null && to == null){
            Date dateF = Date.valueOf(LocalDate.now().minusYears(3).toString());
            Date dateT = Date.valueOf(LocalDate.now().toString());
            return bookRepository.findBookEntityByPubDateBetween(dateF, dateT, nextPage);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date dateF = format.parse(from);
            Date dateFrom = new Date(dateF.getTime());

            java.util.Date dateT = format.parse(to);
            Date dateTo = new Date(dateT.getTime());
            return bookRepository.findBookEntityByPubDateBetween(dateFrom, dateTo, nextPage);
        }
    }

    public List<BookEntity> getBooksByAuthorId(Integer id){
        return bookRepository.findBookEntityByAuthorId(id);
    }
}
