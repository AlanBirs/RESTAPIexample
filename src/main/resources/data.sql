DROP TABLE IF EXISTS books_catalog;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS student;


CREATE TABLE student (
  id INT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE author (
  id INT AUTO_INCREMENT  PRIMARY KEY NOT NULL,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_author INT NOT NULL,
    name VARCHAR (100) NOT NULL,
    FOREIGN KEY (id_author) REFERENCES author (id)
);

CREATE TABLE books_catalog (
    id_student INT NOT NULL,
    id_book INT NOT NULL,
    PRIMARY KEY (id_student, id_book),
    FOREIGN KEY (id_student) REFERENCES student (id),
    FOREIGN KEY (id_book) REFERENCES book (id)
);

insert into student (id, name) values (1, 'Student1');
insert into student (id, name) values (2, 'Student2');
insert into student (id, name) values (3, 'Student3');
insert into student (id, name) values (4, 'Student4');
insert into student (id, name) values (5, 'Student5');

insert into author (id, name) values (1, 'Jacque Fresco');
insert into author (id, name) values (2, 'Author2');
insert into author (id, name) values (3, 'Author3');
insert into author (id, name) values (4, 'Author4');
insert into author (id, name) values (5, 'Author5');

insert into book (id, id_author, name) values (1, 1, 'The Best that Money Can`t Buy: Beyond Politics, Poverty & War');