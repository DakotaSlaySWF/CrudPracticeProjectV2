package com.galvanize.CrudPracticeProjectV2.Book;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static java.time.LocalDateTime.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository repository;

    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private SimpleDateFormat df;


    @BeforeEach
    void setUp() {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        objectMapper.setDateFormat(df);
    }

    @Test
    @Transactional
    @Rollback
    void testPostRequestToAddOneEntityToDatabase() throws Exception{
        Book bookOne = new Book();
        bookOne.setName("Spring is Awesome");
        bookOne.setPublishDate(Timestamp.valueOf(of(2022,4,5,4,0)));

        String json = objectMapper.writeValueAsString(bookOne);

        MockHttpServletRequestBuilder request = post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Spring is Awesome")))
                .andExpect(jsonPath("$.publishDate", is("2022-04-05 09:00")));


    }

    @Test
    @Transactional
    @Rollback
    void testGetRequestToGetOneEntityToDatabase() throws Exception{
        Book bookOne = new Book();
        bookOne.setName("Spring is Awesome");
        bookOne.setPublishDate(Timestamp.valueOf(of(2022,4,5,4,0)));

        this.repository.save(bookOne);

        MockHttpServletRequestBuilder request = get("/books/"+bookOne.getId());

        this.mvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Spring is Awesome")))
                .andExpect(jsonPath("$.publishDate", is("2022-04-05 09:00")));


    }

    @Test
    @Transactional
    @Rollback
    void testPatchRequestToChangeOneEntityInDatabase() throws Exception{
        Book bookOne = new Book();
        bookOne.setName("Spring is Awesome");
        bookOne.setPublishDate(Timestamp.valueOf(of(2022,4,5,4,0)));

        this.repository.save(bookOne);

        Map<String,Object> bookOneMutated = new HashMap<>();
        bookOneMutated.put("name","Spring is the Best");
        Date date = Timestamp.valueOf(of(2022,4,5,4,0));
        bookOneMutated.put("publishDate",date);

        String json = objectMapper.writeValueAsString(bookOneMutated);

        MockHttpServletRequestBuilder request = patch("/books/"+bookOne.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Spring is the Best")))
                .andExpect(jsonPath("$.publishDate", is("2022-04-05 09:00")));


    }

    @Test
    @Transactional
    @Rollback
    void testDeleteRequestToDeleteOneEntityInDatabase() throws Exception{
        Book bookOne = new Book();
        bookOne.setName("Spring is Awesome");
        bookOne.setPublishDate(Timestamp.valueOf(of(2022,4,5,4,0)));

        this.repository.save(bookOne);


        MockHttpServletRequestBuilder request = delete("/books/"+bookOne.getId());

        this.mvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Spring is Awesome")))
                .andExpect(jsonPath("$.publishDate", is("2022-04-05 09:00")));


    }
    @Test
    @Transactional
    @Rollback
    void testGetRequestToGetListOfDatabase() throws Exception{
        MockHttpServletRequestBuilder request = get("/books");

        this.mvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").isEmpty());


    }


}
