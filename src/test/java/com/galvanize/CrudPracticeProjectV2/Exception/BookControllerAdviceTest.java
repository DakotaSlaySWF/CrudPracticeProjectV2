package com.galvanize.CrudPracticeProjectV2.Exception;


import com.galvanize.CrudPracticeProjectV2.Book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerAdviceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository repository;

    @Test
    @Transactional
    @Rollback
    void testNoSuchElementFoundException() throws Exception {
        this.mvc.perform(get("/books/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Such ElementFound"));
    }
}
