package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.file.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFileEntity, Integer> {
    public BookFileEntity findBookFileByHash(String hash);
}
