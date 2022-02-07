package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.BookReviewLikeService;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {
    private BookReviewLikeService bookReviewLikeService;

    @Autowired
    public ReviewController(BookReviewLikeService bookReviewLikeService) {
        this.bookReviewLikeService = bookReviewLikeService;
    }

    @PostMapping("/book/rateBookReview")
    public String postRateBookReview(
            @RequestParam("value") short value,
            @RequestParam("likeid") Integer likeid
    ){
        BookReviewLikeEntity bookReviewLike = bookReviewLikeService.getBookReviewLike(likeid);
        bookReviewLikeService.setBookReview(likeid, value);
        return null;
    }
    //return ("redirect:/books/" + bookid);}

    @PostMapping("/book/bookReview/{slug}")
    public String postBookReview(
            @PathVariable("slug") String slug,
            @RequestParam("text") String text
    ){

        return null;
    }
}
