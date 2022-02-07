package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {

    TagsEntity findTagsEntityByTagsName(String name);
}
