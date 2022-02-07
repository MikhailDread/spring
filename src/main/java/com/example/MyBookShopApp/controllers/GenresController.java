package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.services.BooksRatingAndPopulatityService;
import com.example.MyBookShopApp.data.services.GenresService;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class GenresController {

    private final GenresService genresService;
    private final BookRepository bookRepository;
    private final BooksRatingAndPopulatityService booksRatingAndPopulatityService;
    private final BookService bookService;

    @Autowired
    public GenresController(GenresService genresService,
                            BookRepository bookRepository,
                            BooksRatingAndPopulatityService booksRatingAndPopulatityService,
                            BookService bookService) {
        this.genresService = genresService;
        this.bookRepository = bookRepository;
        this.booksRatingAndPopulatityService = booksRatingAndPopulatityService;
        this.bookService = bookService;
    }

    @ModelAttribute("genresListBetween")
    public List<GenreEntity> getGenresBetween(){
        return genresService.getGenresDataBetween(0, 20);
    }

    @ModelAttribute("genresList")
    public Map<String, List<GenreEntity>> getGenres(){
        return genresService.getGenresData();
    }

    @GetMapping("/genres")
    public String genresPage(){
        booksRatingAndPopulatityService.calculatePopularBooks();
        return "/genres/index";
    }

    @GetMapping("/genres/{slug}")
    public String genresSlugPage(@PathVariable("slug") String slug, Model model){
        GenreEntity genreBySlug = genresService.getGenreBySlug(slug);
        List<BookEntity> booksByGenres = bookRepository.findBookEntityByGenreId(genreBySlug.getId());
        model.addAttribute("choiceGenre", genreBySlug);
        model.addAttribute("slugGenres", booksByGenres);
        return "/genres/slug";
    }

    @GetMapping("/books/genre/{magicNumber}")
    @ResponseBody
    public BooksPageDto genresSlugPage(
            @PathVariable("magicNumber") Integer magicNumber,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return new BooksPageDto(bookService.getBookCompilation(offset, limit).getContent());
    }
}
