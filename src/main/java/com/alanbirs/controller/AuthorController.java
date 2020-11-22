package com.alanbirs.controller;

import com.alanbirs.model.Author;
import com.alanbirs.repository.AuthorRepository;
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
@Api(value = "authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;


    @ApiOperation(value = "Returns authors pageable")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 500, message = "Error"),
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping("/authors")
    public ResponseEntity<Map<String, Object>> getAllAuthors(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Author> authors;
            Pageable paging = PageRequest.of(page, size);

            Page<Author> pageTuts;
            if (name == null)
                pageTuts = authorRepository.findAll(paging);
            else
                pageTuts = authorRepository.findByNameContaining(name, paging);

            authors = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("authors", authors);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Returns author by Id")
    @GetMapping("/authors/{id}")
    public ResponseEntity<Map<String, Object>> getAuthorById(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();
            Author author = authorRepository.findFirstById(id);
            response.put("author", author);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Create author")
    @PostMapping("/authors")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Author author){
        try {
            Map<String, Object> response = new HashMap<>();
            authorRepository.save(author);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Edit author")
    @PutMapping("/authors/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable int id, @RequestBody Author author) {
        try {
            Map<String, Object> response = new HashMap<>();

            Author authorFromDB = authorRepository.findFirstById(id);
            authorFromDB.setName(author.getName());
            authorRepository.save(authorFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete author")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();

            Author authorFromDB = authorRepository.findFirstById(id);
            authorRepository.delete(authorFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}