package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.AuthorService;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.struct.author.AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<AuthorEntity>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @ApiOperation("to get map of authors")
    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String,List<AuthorEntity>> authors(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors/{slug}")
    public String getAuthorPage(
            @PathVariable("slug") String slug,
            Model model
    ){
        AuthorEntity author = authorService.getAuthorBySlug(slug);
        List<BookEntity> byAuthorId = bookService.getBooksByAuthorId(author.getId());
        model.addAttribute("authorById", author);
        model.addAttribute("booksById", byAuthorId);
        return "/authors/slug";
    }

    @ModelAttribute("authorPageBooks")
    public List<BookEntity> authorsBooks(AuthorEntity author){
        return bookService.getBookAuthorCompilation(0, 20, author).getContent();
    }
}
