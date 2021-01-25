package org.example.app.services;


import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookFilter;
import org.example.web.exceptions.BookSaveException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class FilterService {

    BookFilter bookFilter;

    private final Logger logger = Logger.getLogger(FilterService.class);

    public BookFilter getBookFilter() {
        return bookFilter;
    }

    public void setBookFilter(BookFilter bookFilter) {
        this.bookFilter = bookFilter;
    }

    private List<Book> filter(List<Book> bookList, String value, String row) {
        logger.info(String.format("filter: {value=%s,row=%s}", value, row));
        switch (row) {
            case "id":
                return bookList.stream()
                        .filter(book -> String.valueOf(book.getId()).matches(value)).collect(Collectors.toList());
            case "author":
                return bookList.stream()
                        .filter(book -> book.getAuthor().matches(value)).collect(Collectors.toList());
            case "book_title":
                return bookList.stream().
                        filter(book -> book.getTitle().matches(value)).collect(Collectors.toList());
            case "size":
                return bookList.stream()
                        .filter(book -> String.valueOf(book.getSize()).matches(value)).collect(Collectors.toList());
        }
        return bookList;
    }

    public List<Book> getFilterBook(List<Book> bookList) {
        if ((bookFilter != null) && (bookFilter.getValue() != null) && !bookFilter.getValue().isEmpty()) {
            return filter(bookList, bookFilter.getValue(), bookFilter.getRow());
        }
        return bookList;
    }

    public void validateFilter(BookFilter bookFilter) throws BookSaveException {
        switch (bookFilter.getRow()) {
            case "size": {
                if (!bookFilter.getValue().replaceAll("\\p{P}", "").matches("\\d+"))
                    throw new BookSaveException("format required: \\d ");
            }
        }

    }

}
