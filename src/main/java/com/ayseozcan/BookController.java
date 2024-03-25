package com.ayseozcan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
class BookController {

    private final BookRepository bookRepository;


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/find-all")
    ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}
