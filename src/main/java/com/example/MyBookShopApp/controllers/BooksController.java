package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.services.AuthorService;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.services.BooksRatingAndPopulatityService;
import com.example.MyBookShopApp.data.services.ResourceStorage;
import com.example.MyBookShopApp.data.struct.author.AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class BooksController {

    private final BookService bookService;
    private BookRepository bookRepository;
    private BooksRatingAndPopulatityService booksRatingAndPopulatityService;
    private ResourceStorage storage;
    private AuthorService authorService;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, BooksRatingAndPopulatityService booksRatingAndPopulatityService, ResourceStorage storage, BookRepository bookRepository) {
        this.bookService = bookService;
        this.booksRatingAndPopulatityService = booksRatingAndPopulatityService;
        this.storage = storage;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return new BooksPageDto(bookService.getBookCompilation(offset, limit).getContent());
    }

    @GetMapping("/books/author/{magicNumber}")
    @ResponseBody
    public BooksPageDto getAuthorsBooks(
            @PathVariable("magicNumber") Integer magicNumber,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getBookPopularCompilation(offset, limit).getContent());
    }

    @GetMapping("/books/author/{lastName}/{firstName}")
    public String getAuthorsBooksPage(
            @PathVariable("lastName") String lastName,
            @PathVariable(value = "firstName") String firstName,
            Model model) {
        AuthorEntity author = authorService.getAuthorByLastAndFirstName(lastName, firstName);
        List<BookEntity> byAuthorId = bookService.getBooksByAuthorId(author.getId());
        model.addAttribute("booksAuthorById", author);
        model.addAttribute("booksArrayById", byAuthorId);
        return "/books/author";
    }

    @GetMapping("/books/{slug}")
    public String getBookBySlug(@PathVariable("slug") String slug, Model model){
        BookEntity book = bookRepository.findBookEntityBySlug(slug);
        model.addAttribute("slugBook", book);
        return "/books/slug";
    }

    @GetMapping("/books/author")
    public String getAuthorBooks(){
        return "/books/author";
    }

    @PostMapping("/books/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        BookEntity bookEntityBySlug = bookRepository.findBookEntityBySlug(slug);
        bookEntityBySlug.setImage(savePath);
        bookRepository.save(bookEntityBySlug); //save new path in db

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/books/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash")String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: "+path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: "+mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: "+data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}
