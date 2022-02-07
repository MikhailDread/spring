package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.AuthorRepository;
import com.example.MyBookShopApp.data.struct.author.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Map<String, List<AuthorEntity>> getAuthorsMap(){
        List<AuthorEntity> authors = authorRepository.findAll();

        return authors.stream().collect(Collectors.groupingBy((AuthorEntity a)->{return a.getLastName().substring(0,1);}));
    }

    public AuthorEntity getAuthorByLastAndFirstName(String lastname, String firstname){
        return authorRepository.findAuthorEntityByLastNameAndFirstName(lastname, firstname);
    }

    public AuthorEntity getAuthorBySlug(String slug){
        return authorRepository.findAuthorEntityBySlug(slug);
    }
}
