package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PopularController {
    private final BookService bookService;

    @Autowired
    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/popular_book")
    public String popularPage(){
        return "/books/popular";
    }

    @ModelAttribute("popularPageBooks")
    public List<BookEntity> popularBooks(){
        return bookService.getBookPopularCompilation(0, 20).getContent(); }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooks(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getBookPopularCompilation(offset, limit).getContent());
    }
}
