package com.example.redisdoubledeletedemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Student {
    private Integer id;

    private String numbercode;

    private String stuname;

    private String stusex;

    private Integer stuage;


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", numbercode='" + numbercode + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stusex='" + stusex + '\'' +
                ", stuage=" + stuage +
                '}';
    }
}

