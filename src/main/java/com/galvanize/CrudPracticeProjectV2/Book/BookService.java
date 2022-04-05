package com.galvanize.CrudPracticeProjectV2.Book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

@Service
public class BookService {

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Book> patchItemInDatabase(Long id, Map<String, Object> map) {
        Book oldBook = repository.findById(id).get();
        map.forEach((key, value)->{
            if(key.equals("publishDate"))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                df.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                try {
                    oldBook.setPublishDate(df.parse(String.valueOf(value)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                Field field = ReflectionUtils.findField(Book.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,oldBook,value);
            }
        });
        return new ResponseEntity<>(this.repository.save(oldBook), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Book> deleteItemFromDatebase(Long id) {
        Book deletedBook = repository.findById(id).get();
        repository.deleteById(id);
        return new ResponseEntity<>(deletedBook,HttpStatus.ACCEPTED);
    }
}
