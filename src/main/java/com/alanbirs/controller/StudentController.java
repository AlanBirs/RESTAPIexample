package com.alanbirs.controller;

import com.alanbirs.model.Student;
import com.alanbirs.repository.StudentRepository;
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
@Api(value = "students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @ApiOperation(value = "Returns students pageable")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 500, message = "Error"),
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> getAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Student> students;
            Pageable paging = PageRequest.of(page, size);

            Page<Student> pageTuts;
            if (name == null)
                pageTuts = studentRepository.findAll(paging);
            else
                pageTuts = studentRepository.findByNameContaining(name, paging);

            students = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("students", students);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Returns student by id")
    @GetMapping("/student/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();
            Student student = studentRepository.findFirstById(id);
            response.put("student", student);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Create student")
    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Student student){
        try {
            Map<String, Object> response = new HashMap<>();
            studentRepository.save(student);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update student")
    @PutMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable int id, @RequestBody Student student) {
        try {
            Map<String, Object> response = new HashMap<>();

            Student studentFromDB = studentRepository.findFirstById(id);
            studentFromDB.setName(student.getName());
            studentRepository.save(studentFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete student")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        try {
            Map<String, Object> response = new HashMap<>();

            Student authorFromDB = studentRepository.findFirstById(id);
            studentRepository.delete(authorFromDB);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
