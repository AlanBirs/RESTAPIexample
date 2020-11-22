package com.alanbirs.controller;

import com.alanbirs.model.Author;
import com.alanbirs.model.Book;
import com.alanbirs.model.Student;
import com.alanbirs.repository.AuthorRepository;
import com.alanbirs.repository.BookRepository;
import com.alanbirs.repository.StudentRepository;
import com.alanbirs.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping()
    public List<Student> getAll(){
        return studentRepository.findAll();
    }


    @PostMapping
    public Student create(@RequestBody Student student){

        Author author = new Author("Jacque Fresco");
        authorRepository.save(author);

        Book book = new Book("The Best that Money Can't Buy: Beyond Politics, Poverty & War");
        book.setAuthor(author);
        bookRepository.save(book);
        List<Book> books = new ArrayList<Book>();
        books.add(book);
        student.setBooks(books);
        studentRepository.save(student);
        return student;
    }
}
