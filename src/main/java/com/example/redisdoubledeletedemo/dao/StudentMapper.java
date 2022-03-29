package com.example.redisdoubledeletedemo.dao;

import com.example.redisdoubledeletedemo.model.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
@Component
public interface StudentMapper {

    @Select("select * from student")
    List<Student> selectAll();

    @Select("select * from student where numbercode = #{numberCode}")
    Student getStudent(@Param("numberCode") String numberCode);

    @Delete("delete from student where id=#{id}")
    int delete(@Param("numberCode") String numberCode);

    @Update("update student set numbercode=#{numbercode},stuname=#{stuname},stusex=#{stusex},stuage=#{stuage} where id=#{id}")
    int update(Student student);

//    @Insert(" insert into student(numbercode,stuname,stusex,stuage)\n" +
//            "        values(#{numbercode},#{stuname},#{stusex},#{stuage})")
    int insert(Student student);
}
