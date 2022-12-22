package com.example.StudentApp.controller;


import com.example.StudentApp.Exception.ResourceNotFoundException;
import com.example.StudentApp.Repository.StudentRepository;
import com.example.StudentApp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/Students")
public class StudentController {

    @Autowired
    private StudentRepository StudentRepository;

    @GetMapping
    public List<Student> getAllStudents(){
        return StudentRepository.findAll();
    }


    @PostMapping
    public Student createStudent(@RequestBody Student Student) {
        return StudentRepository.save(Student);
    }


    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable  long id){
        Student Student = StudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id:" + id));
        return ResponseEntity.ok(Student);
    }


    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id,@RequestBody Student StudentDetails) {
        Student updateStudent = StudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id: " + id));

        updateStudent.setName(StudentDetails.getName());
        updateStudent.setEmail(StudentDetails.getEmail());
        updateStudent.setAddress(StudentDetails.getAddress());
        updateStudent.setMobile(StudentDetails.getMobile());
        updateStudent.setDob(StudentDetails.getDob());

        StudentRepository.save(updateStudent);

        return ResponseEntity.ok(updateStudent);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable long id){

        Student Student = StudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id: " + id));

        StudentRepository.delete(Student);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}