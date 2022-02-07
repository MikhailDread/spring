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

import java.text.ParseException;
import java.util.List;

@Controller
public class RecentController {
    private final BookService bookService;

    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/recent_book")
    public String recentPage(){
        return "/books/recent";
    }

    @ModelAttribute("recentPageBooks")
    public List<BookEntity> recentBooks(){return bookService.getBookRecentCompilation(0, 20).getContent(); }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooks(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) throws ParseException {
        return new BooksPageDto(bookService.getBookWithRecentAndData(from, to, offset, limit).getContent());
    }
}
