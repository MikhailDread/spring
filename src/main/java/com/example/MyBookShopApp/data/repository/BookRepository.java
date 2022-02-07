package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    List<BookEntity> findBooksByAuthor_FirstName(String name);

    @Query("from BookEntity")
    List<BookEntity> customFindAllBooks();

    //NEW BOOK REPOSITORY

    List<BookEntity> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<BookEntity> findBooksByTitleContaining(String bookTitle);

    List<BookEntity> findBooksByPriceOldBetween(Integer min, Integer max);

    List<BookEntity> findBooksByPriceOldIs(Integer price);

    @Query("from BookEntity where isBestseller=1")
    List<BookEntity> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<BookEntity> getBooksWithMAxDiscount();

    Page<BookEntity> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    Page<BookEntity> findBookEntityByPubDateBetween(Date from, Date to, Pageable nextPage);

    BookEntity findBookEntityBySlug(String slug);

  //  Page<BookEntity> findBookEntityByMostPopular(Pageable nextPage);

    List<BookEntity> findBookEntityByTagId(Integer tagId);

    List<BookEntity> findBookEntityByGenreId(Integer genreId);

    List<BookEntity> findBookEntityByAuthorId(Integer id);

    Page<BookEntity> findBookEntityByAuthorId(Pageable nextPage, Integer id);

    List<BookEntity> findBooksBySlug(String[] slugs);
}
