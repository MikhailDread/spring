package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.ApiResponse;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "books config")
public class BooksRestApiController {

    private final BookService bookService;

    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<BookEntity>> bookByAuthor(@RequestParam(value = "author") String authorName){
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books by book title")
    public ResponseEntity<ApiResponse<BookEntity>> bookByTitle(@RequestParam(value = "title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<BookEntity> response = new ApiResponse<>();
        List<BookEntity> data = bookService.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get books in price range")
    public ResponseEntity<List<BookEntity>> priceRangeBooks(@RequestParam(value = "min") Integer min, @RequestParam("max") Integer max){
        return ResponseEntity.ok(bookService.getBookWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-price")
    @ApiOperation("get books with max price")
    public ResponseEntity<List<BookEntity>> maxPriceBooks(){
        return ResponseEntity.ok(bookService.getBooksWithMaxPrice());
    }

    @GetMapping("/books/bestsellers")
    @ApiOperation("get bestseller books")
    public ResponseEntity<List<BookEntity>> bestSellerBooks(){
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<BookEntity>> handleMissingServletRequestParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<BookEntity>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<BookEntity>> handleBookstoreApiWrongParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<BookEntity>(HttpStatus.BAD_REQUEST, "Bad parameter value...",exception)
                ,HttpStatus.BAD_REQUEST);
    }
}
