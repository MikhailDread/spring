package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.struct.book.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
public class PostponedController {

    private final BookRepository bookRepository;

    @Autowired
    public PostponedController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute(name = "bookPostponed")
    public List<BookEntity> bookPostponed(){
        return new ArrayList<>();
    }

    @GetMapping("/book/postponed")
    public String getPostponedBooks(@CookieValue(value = "cartContents", required = false) String cartContents, Model model){
        if(cartContents == null || cartContents.equals("")){
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            List<BookEntity> booksFromCookiesSlugs = bookRepository.findBooksBySlug(cookiesSlugs);
            model.addAttribute("bookCart", booksFromCookiesSlugs);
        }
        return "postponed";
    }

    @PostMapping("/book/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(
            @PathVariable("slug") String slug,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            HttpServletResponse httpServletResponse,
            Model model
    ){
        if(cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/book");
            httpServletResponse.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/book/postponed";
    }
}

