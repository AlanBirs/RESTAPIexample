package com.alanbirs.repository;

import com.alanbirs.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Page<Author> findByNameContaining(String name, Pageable pageable);
    Page<Author> findAll(Pageable pageable);
    Author findFirstById(int id);
}
