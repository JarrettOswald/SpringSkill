package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    //    private final Set<Book> repo = new HashSet<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemolate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemolate) {
        this.jdbcTemolate = jdbcTemolate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemolate.query("SELECT * FROM books", (ResultSet rs, int rowName) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemolate.update("INSERT INTO books(author,title,size) VALUES(:author,:title,:size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(int bookIdRemove) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", bookIdRemove);
        jdbcTemolate.update("DELETE FROM books WHERE id = :id", mapSqlParameterSource);
        logger.info("remove book: " + bookIdRemove);
        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit() {
        logger.info("default INIT in book service");
    }

    public void defaultDestroy() {
        logger.info("default destroy in service");
    }
}
