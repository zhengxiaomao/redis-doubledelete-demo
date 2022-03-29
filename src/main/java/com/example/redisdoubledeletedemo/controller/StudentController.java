package com.example.redisdoubledeletedemo.controller;

import com.example.redisdoubledeletedemo.model.Student;
import com.example.redisdoubledeletedemo.service.StudentService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping( path = "/getList")
    public List<Student> getList(){
        List<Student> students = studentService.selectAll();
        return students;
    }

    @GetMapping( path = "/getStudent")
    public String getList(@Param("numberCode") String numberCode){
        Student students = studentService.getStudent(numberCode);
        String str = new Gson().toJson(students);
        return str;
    }

    @PostMapping(path = "/insert")
    public String insert(@RequestBody Student student){
        int insert = studentService.insert(student);
        String msg = "";
        if( insert > 0 ){
            msg = "{\"msg\":\"新增成功\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"新增失败\",\"flag\":false}";
        }
        return msg;
    }

    @GetMapping(path = "/delete")
    public String delete(@Param("numberCode") String numberCode){
        int delete = studentService.delete(numberCode);
        String msg = "";
        if( delete > 0 ){
            msg = "{\"msg\":\"删除成功！！\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"删除失败！！\",\"flag\":false}";
        }
        return msg;
    }

    @PostMapping(path = "/update")
    public String update(@RequestBody Student student) throws InterruptedException {
        int update = studentService.update(student);
        String msg = "";
        if( update > 0 ){
            msg = "{\"msg\":\"更新成功！！！\",\"flag\":true}";
        }else {
            msg = "{\"msg\":\"更新失败！！！\",\"flag\":false}";
        }
        return msg;
    }

}