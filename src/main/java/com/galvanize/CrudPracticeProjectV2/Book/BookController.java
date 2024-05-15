package com.galvanize.CrudPracticeProjectV2.Book;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookRepository repository;
    private final BookServicePort service;

    @PostMapping("")
    public ResponseEntity<Book> save(@RequestBody Book entity) {
        return new ResponseEntity<>(this.repository.save(entity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id)
    {
        return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchItemInDatabase(@PathVariable Long id, @RequestBody Map<String,Object> map)
    {
        return ResponseEntity.accepted().body(service.patchBook(id, map));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteItemFromDatabase(@PathVariable Long id)
    {
        return ResponseEntity.accepted().body(service.deleteBook(id));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getListOfItemsFromDatabase()
    {
        return new ResponseEntity<>(this.repository.findAll(),HttpStatus.ACCEPTED);
    }
}
