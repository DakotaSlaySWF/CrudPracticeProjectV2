package com.galvanize.CrudPracticeProjectV2.Book;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BookService implements BookServicePort {

    private final BookRepository repository;

    @Override
    public Book patchBook(Long id, Map<String, Object> map) {
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
        return this.repository.save(oldBook);
    }

    @Override
    public Book deleteBook(Long id) {
        Book deletedBook = repository.findById(id).get();
        repository.deleteById(id);
        return deletedBook;
    }
}
