package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.FAQService;
import com.example.MyBookShopApp.data.struct.other.FaqEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class FAQController {

    private FAQService faqService;

    @Autowired
    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @ModelAttribute("faqList")
    public List<FaqEntity> getFaqData(){
        return faqService.getFaqList();
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "faq";
    }
}
