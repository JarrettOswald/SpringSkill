package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public boolean saveBook(Book book) {
        if (book.getSize() != null || !book.getTitle().isEmpty() || !book.getAuthor().isEmpty()) {
            bookRepo.store(book);
            return true;
        }
        logger.info("saveBook: save new " + book);
        return false;
    }

    public void removeBookBy(BookToRemove bookToRemove) {
        String value = bookToRemove.getValue();

        for (Book book : getAllBooks()) {
            switch (bookToRemove.getRow()) {
                case "id":
                    if (String.valueOf(book.getId()).matches(value)) {
                        bookRepo.removeItemById(book.getId());
                    }
                    break;
                case "author":
                    if (book.getAuthor().matches(value)) {
                        bookRepo.removeItemById(book.getId());
                    }
                    break;
                case "book_title":
                    if (book.getTitle().matches(value)) {
                        bookRepo.removeItemById(book.getId());
                    }
                    break;
                case "size":
                    if (String.valueOf(book.getSize()).matches(value)) {
                        bookRepo.removeItemById(book.getId());
                    }
                    break;
            }
        }
    }
}
