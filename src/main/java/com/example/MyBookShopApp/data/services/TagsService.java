package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.struct.book.TagsEntity;
import com.example.MyBookShopApp.data.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    private TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<TagsEntity> getTagsList(){
        return tagsRepository.findAll();
    }
}
