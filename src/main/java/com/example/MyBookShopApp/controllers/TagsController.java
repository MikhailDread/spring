package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.TagsRepository;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.example.MyBookShopApp.data.struct.book.TagsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/tags")
public class TagsController {

    private BookRepository bookRepository;
    private TagsRepository tagsRepository;

    @Autowired
    public TagsController(BookRepository bookRepository, TagsRepository tagsRepository) {
        this.bookRepository = bookRepository;
        this.tagsRepository = tagsRepository;
    }

    @GetMapping("{tagsName}")
    public String tagBookPage(@PathVariable("tagsName") String tagsName, Model model){
        TagsEntity tagsEntity = tagsRepository.findTagsEntityByTagsName(tagsName);
        List<BookEntity> bookEntityByTag = bookRepository.findBookEntityByTagId(tagsEntity.getId());
        model.addAttribute("tagBook", bookEntityByTag);
        return "/tags/index";
    }
}
