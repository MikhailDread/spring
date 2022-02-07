package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.GenresRepository;
import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenresService {

    private GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public Map<String, List<GenreEntity>> getGenresData(){
        List<GenreEntity> genresList = genresRepository.findAll();
        return genresList.stream().collect(Collectors.groupingBy(GenreEntity::getCategory));
    }

    public List<GenreEntity> getGenresDataBetween(Integer offset, Integer limit){
        Pageable page = PageRequest.of(offset, limit);
        return genresRepository.findAll(page).getContent();
    }

    public GenreEntity getGenreBySlug(String slug){
        return genresRepository.findGenreEntitiesBySlug(slug);
    }
}
