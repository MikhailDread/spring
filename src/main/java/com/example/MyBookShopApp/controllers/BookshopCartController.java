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
@RequestMapping("/book")
public class BookshopCartController {

    @ModelAttribute(name = "bookCart")
    public List<BookEntity> bookCart(){
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    @Autowired
    public BookshopCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/cart")
    public String handlerCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents, Model model){
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
        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(
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
        return "redirect:/book/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(
            @PathVariable("slug") String slug,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            HttpServletResponse response,
            Model model
    ){
        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/book");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if(!cartContents.contains(slug)){
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/book");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        return ("redirect:/books/" + slug);
    }
}
