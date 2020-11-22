package com.alanbirs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_author")
    private Author author;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(int id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @JsonIgnore
    public List<Student> getStuents() {
        return students;
    }

    public void setStuents(List<Student> stuents) {
        this.students = stuents;
    }
}
