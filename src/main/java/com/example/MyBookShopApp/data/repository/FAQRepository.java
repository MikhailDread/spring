package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.other.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FAQRepository extends JpaRepository<FaqEntity, Integer> {
}
