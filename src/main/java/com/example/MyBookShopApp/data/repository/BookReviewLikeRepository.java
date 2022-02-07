package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {
}
