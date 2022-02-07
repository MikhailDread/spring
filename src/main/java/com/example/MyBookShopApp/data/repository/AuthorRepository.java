package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    AuthorEntity findAuthorEntityByLastNameAndFirstName(String lastName, String firstName);

    AuthorEntity findAuthorEntityBySlug(String slug);
}
