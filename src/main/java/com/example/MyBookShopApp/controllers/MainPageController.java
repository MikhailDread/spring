package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.services.BooksRatingAndPopulatityService;
import com.example.MyBookShopApp.data.services.TagsService;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.example.MyBookShopApp.data.struct.book.TagsEntity;
import com.example.MyBookShopApp.errs.EmptySearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagsService tagsService;
    private final BooksRatingAndPopulatityService booksRatingAndPopulatityService;

    @Autowired
    public MainPageController(BookService bookService, TagsService tagsService, BooksRatingAndPopulatityService booksRatingAndPopulatityService) {
        this.bookService = bookService;
        this.tagsService = tagsService;
        this.booksRatingAndPopulatityService = booksRatingAndPopulatityService;
    }

    @ModelAttribute("recommendedBooks")
    public List<BookEntity> recommendedBooks(){
        return bookService.getBookCompilation(0, 6).getContent();
    }

    //Метод написан на подобии метода (recommendedBooks) из уроков про пагинацию страниц, кидает ошибку 0
    @ModelAttribute("recentBooks")
    public List<BookEntity> recentBooks(){return bookService.getBookRecentCompilation(0, 6).getContent(); }

    //Метод написан на подобии метода (recommendedBooks) из уроков про пагинацию страниц, кидает ошибку 0
    @ModelAttribute("popularBooks")
    public List<BookEntity> popularBooks(){
        return bookService.getBookPopularCompilation(0, 6).getContent(); }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResult")
    public List<BookEntity> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(
            @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
            Model model) throws EmptySearchException {
        if(searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute(
                    "searchResult",
                    bookService.getPageOfSearchResultBook(searchWordDto.getExample(), 0, 5).getContent()
            );
            return "/search/index";
        }
        else {
            throw new EmptySearchException("Search with null impossible");
        }
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto
    ){
        return new BooksPageDto(bookService.getPageOfSearchResultBook(searchWordDto.getExample(), offset, limit).getContent());
    }

    @ModelAttribute("tagsList")
    public List<TagsEntity> getTagsList(){
        return tagsService.getTagsList();
    }
}
