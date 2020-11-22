package com.alanbirs.repository;

import com.alanbirs.model.Author;
import com.alanbirs.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findByNameContaining(String name, Pageable pageable);
    Page<Student> findAll(Pageable pageable);
    Student findFirstById(int id);
}
