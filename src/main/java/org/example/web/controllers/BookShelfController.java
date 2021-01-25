package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.app.services.FileService;
import org.example.app.services.FilterService;
import org.example.web.dto.Book;
import org.example.web.dto.BookToRemove;
import org.example.web.dto.BookFilter;
import org.example.web.dto.FileInRepo;
import org.example.web.exceptions.BookSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private final FilterService filterService;
    private final FileService fileService;

    @Autowired
    public BookShelfController(BookService bookService, FilterService filterService, FileService fileService) {
        this.bookService = bookService;
        this.filterService = filterService;
        this.fileService = fileService;

    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("filter", new BookFilter());
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("bookList", filterService.getFilterBook(bookService.getAllBooks()));
        model.addAttribute("fileList", fileService.getListFile());
        return "book_shelf";
    }

    @ExceptionHandler(BookSaveException.class)
    public String exceptionSveBook(Model model, BookSaveException bookSaveException) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("filter", new BookFilter());
        model.addAttribute("bookList", filterService.getFilterBook(bookService.getAllBooks()));
        model.addAttribute("errorMessage", bookSaveException.getMessage());
        model.addAttribute("fileList", fileService.getListFile());
        return "book_shelf";
    }


    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) throws BookSaveException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", filterService.getFilterBook(bookService.getAllBooks()));
            return "book_shelf";
        }
        if (!bookService.saveBook(book)) {
            throw new BookSaveException("At least one field must be filled");
        }
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        logger.info(bookIdToRemove);
        if (bindingResult.hasErrors()) {
            logger.info("hasErrors() == true");
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", filterService.getFilterBook(bookService.getAllBooks()));
            return "book_shelf";
        } else {
            bookService.removeBookBy(bookIdToRemove);
            return "redirect:/books/shelf";
        }
    }

    @GetMapping("/filter")
    public String sortBook(BookFilter bookFilter) throws BookSaveException {
        logger.info("fialter params:\n" + bookFilter);
        filterService.validateFilter(bookFilter);
        filterService.setBookFilter(bookFilter);
        return "redirect:/books/shelf";
    }

    @PostMapping("/filterof")
    public String sortBookOff() {
        logger.info("got book filter");
        filterService.setBookFilter(null);
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));) {
            stream.write(bytes);
        }
        logger.info("new file saved at: " + serverFile.getAbsolutePath());
        return "redirect:/books/shelf";
    }

    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("file_id") String fileId, HttpServletResponse response) throws Exception {
        logger.info("/downloadFile");
        Optional<FileInRepo> file = fileService.getFile(Integer.parseInt(fileId));
        file.ifPresent(fileInRepo -> {
            response.setHeader("Content-disposition", "attachment;filename=" + fileInRepo.getName());
            response.setContentType("APPLICATION/OCTET-STREAM");
            try {
                Files.copy(file.get().getPath(), response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        });
//        return "redirect:/books/shelf";
    }


}
