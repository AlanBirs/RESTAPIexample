package com.alanbirs.controller;

import com.alanbirs.model.Book;
import com.alanbirs.repository.BookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(value = "books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @ApiOperation(value = "Returns books pageable")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 500, message = "Error"),
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Book> books;
            Pageable paging = PageRequest.of(page, size);

            Page<Book> pageTuts;
            if (name == null)
                pageTuts = bookRepository.findAll(paging);
            else
                pageTuts = bookRepository.findByNameContaining(name, paging);

            books = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("books", books);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Returns book by id")
    @GetMapping("/book/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();
            Book book = bookRepository.findFirstById(id);
            response.put("book", book);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Create book")
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Book book){
        try {
            Map<String, Object> response = new HashMap<>();
            bookRepository.save(book);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update book")
    @PutMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable int id, @RequestBody Book book) {
        try {
            Map<String, Object> response = new HashMap<>();

            Book bookFromDB = bookRepository.findFirstById(id);
            bookFromDB.setName(book.getName());
            bookRepository.save(bookFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete book")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();

            Book bookFromDB = bookRepository.findFirstById(id);
            bookRepository.delete(bookFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
