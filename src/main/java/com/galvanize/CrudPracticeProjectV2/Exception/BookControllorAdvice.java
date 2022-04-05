package com.galvanize.CrudPracticeProjectV2.Exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class BookControllorAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BookControllorAdvice.class);

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElement(Exception ex)
    {
        logger.error("NoSuchElementException: ",ex.getMessage());
        return new ResponseEntity<>("No Such ElementFound", HttpStatus.NOT_FOUND);
    }

}
