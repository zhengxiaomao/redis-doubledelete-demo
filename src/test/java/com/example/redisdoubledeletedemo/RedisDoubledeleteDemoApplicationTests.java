package com.example.redisdoubledeletedemo;

import com.example.redisdoubledeletedemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDoubledeleteDemoApplicationTests {


    @Autowired
    StudentService studentService;
    @Test
    void contextLoads() {
        studentService.getStudent("10222");

    }

}
