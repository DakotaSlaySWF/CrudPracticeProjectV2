package com.galvanize.CrudPracticeProjectV2.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookRepository repository;
    private BookService service;

    public BookController(BookRepository repository, BookService service) {
        this.repository = repository;
        this.service = service;
    }

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
        return service.patchItemInDatabase(id,map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteItemFromDatabase(@PathVariable Long id)
    {
        return service.deleteItemFromDatebase(id);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getListOfItemsFromDatabase()
    {
        return new ResponseEntity<>(this.repository.findAll(),HttpStatus.ACCEPTED);
    }
}
