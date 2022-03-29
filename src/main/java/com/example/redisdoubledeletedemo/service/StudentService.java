package com.example.redisdoubledeletedemo.service;

import com.example.redisdoubledeletedemo.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> selectAll();

    Student getStudent(String numberCode);

    int delete(String numberCode);

    int update(Student student) throws InterruptedException;

    int insert(Student student);

}