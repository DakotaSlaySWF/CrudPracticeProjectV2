package com.galvanize.CrudPracticeProjectV2.Book;

import java.util.Map;

public interface BookServicePort {

    Book patchBook(Long id, Map<String, Object> map);

    Book deleteBook(Long id);

}
