package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.BookReviewLikeRepository;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReviewLikeService {

    private BookReviewLikeRepository bookReviewLikeRepository;

    @Autowired
    public BookReviewLikeService(BookReviewLikeRepository bookReviewLikeRepository) {
        this.bookReviewLikeRepository = bookReviewLikeRepository;
    }

    public BookReviewLikeEntity getBookReviewLike(Integer id){
       return bookReviewLikeRepository.getById(id);
    }

    public BookReviewLikeEntity setBookReview(Integer reviewId, short value){
        BookReviewLikeEntity review = getBookReviewLike(reviewId);
        review.setValue((short) (review.getValue() + value));
        return review;
    }
}
