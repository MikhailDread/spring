package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<GenreEntity, Integer> {

    GenreEntity findGenreEntitiesBySlug(String slug);
}
