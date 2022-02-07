package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.FAQRepository;
import com.example.MyBookShopApp.data.struct.other.FaqEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService {

    private FAQRepository faqRepository;

    @Autowired
    public FAQService(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqEntity> getFaqList(){
        return faqRepository.findAll();
    }
}
