package com.neosoft.springsecurity.controller;

import com.neosoft.springsecurity.entity.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/v1/student")
public class StudentManagementController {

    private static final List<Student> students = Arrays.asList(new Student(1,"Amit Raj"),
            new Student(2,"rahul raj"),
            new Student(3,"deepak yadav"));


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINEEADMIN')")
    public List<Student> getAllStudent(){
        return students;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("creating student:"+student);
        System.out.println(student);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer studentId){
        System.out.println("deleting student:"+studentId);
        System.out.println(studentId);

    }
    @GetMapping("/{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINEEADMIN')")
    public void getStudentById(@PathVariable Integer studentId){
        System.out.println("fetching student :"+studentId);
        System.out.println(studentId);


    }
    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Integer studentId,@RequestBody Student student){
        System.out.println("updating student:"+student);
        System.out.println(studentId+" "+student);
    }
}
