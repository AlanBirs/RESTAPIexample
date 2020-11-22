package com.alanbirs.repository;

import com.alanbirs.model.Author;
import com.alanbirs.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findByNameContaining(String name, Pageable pageable);
    Page<Book> findAll(Pageable pageable);
    Book findFirstById(int id);
}
