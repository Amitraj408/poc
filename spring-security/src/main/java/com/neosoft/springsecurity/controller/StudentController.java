package com.neosoft.springsecurity.controller;

import com.neosoft.springsecurity.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/student")
public class StudentController {

   private static final List<Student> students = Arrays.asList(new Student(1,"Amit Raj"),
    new Student(2,"rahul raj"),
           new Student(3,"deepak yadav"));

    @GetMapping("/{studentId}")
    public Student GetStudent(@PathVariable Integer studentId){
        return students.stream().filter(e->e.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("student"+studentId+" is not present"));

    }
}
