package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reader")
@PreAuthorize("hasRole('READER')")
public class ReaderController {

    @Autowired
    private XMLService xmlService;

    @Autowired
    private BookService bookService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "reader/dashboard";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = xmlService.getAllBooks();
        model.addAttribute("books", books);
        return "reader/books";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "reader/search";
    }

    @PostMapping("/search/author")
    public String searchByAuthor(@RequestParam String author, Model model) {
        try {
            List<Book> books = xmlService.searchByAuthor(author);
            model.addAttribute("books", books);
            model.addAttribute("searchType", "по автору: " + author);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reader/search-results";
    }

    @PostMapping("/search/year")
    public String searchByYear(@RequestParam int year, Model model) {
        try {
            List<Book> books = xmlService.searchByYear(year);
            model.addAttribute("books", books);
            model.addAttribute("searchType", "по году: " + year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reader/search-results";
    }

    @PostMapping("/search/category")
    public String searchByCategory(@RequestParam String category, Model model) {
        try {
            List<Book> books = xmlService.searchByCategory(category);
            model.addAttribute("books", books);
            model.addAttribute("searchType", "по категории: " + category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reader/search-results";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Получаем информацию о пользователе и взятых книгах
        model.addAttribute("username", username);
        // Добавить логику для отображения взятых книг

        return "reader/account";
    }
}