package com.example.redisdoubledeletedemo.service;

import com.example.redisdoubledeletedemo.dao.StudentMapper;
import com.example.redisdoubledeletedemo.model.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Student> selectAll() {

        String key = "student_list";
        Boolean hasKey = redisTemplate.hasKey(key);

        ValueOperations operations = redisTemplate.opsForValue();

        if (hasKey) {
            String redisList = (String) operations.get(key);

            Type type = new TypeToken<List<Student>>() {}.getType();
            List<Student> list =  new Gson().fromJson(redisList,type);

            System.out.println("StudentServiceImpl.selectAll() : 从缓存取得数据，条数：" + list.size());
            return list;
        }
        List<Student> list = studentMapper.selectAll();
        String toJson = new Gson().toJson(list);
        // 存在到缓存中
        operations.set(key, toJson, 10, TimeUnit.SECONDS);
        return list;
    }

    /**
     * 获取 一位 学生逻辑：
     * 如果缓存存在，从缓存中获取学生信息
     * 如果缓存不存在，从 DB 中获取学生信息，然后插入缓存
     */
    @Override
    public Student getStudent(String numberCode) {
        // 从缓存中 取出学生信息
        String key = "student_" + numberCode;
        Boolean hasKey = redisTemplate.hasKey(key);

        ValueOperations operations = redisTemplate.opsForValue();
        // 缓存存在
        if (hasKey) {
            String str = (String) operations.get(key);
            Student student = new Gson().fromJson(str, Student.class);
            System.out.println("StudentServiceImpl.getStudent() : 从缓存取得数据 >> " + student.toString());
            return student;
        }
        Student student = studentMapper.getStudent(numberCode);
        String str = new Gson().toJson(student);
        // 插入缓存中
        operations.set(key, str, 1000, TimeUnit.DAYS);
        System.out.println("StudentServiceImpl.getStudent() : 学生信息插入缓存 >> " + student.toString());
        return student;
    }

    @Override
    public int delete(String numberCode) {

        String key = "student_" + numberCode;
        Boolean hasKey = redisTemplate.hasKey(key);

        int delete = studentMapper.delete(numberCode);
        if( delete > 0){
            // 缓存存在，进行删除
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("StudentServiceImpl.update() : 从缓存中删除编号学生 >> " + numberCode);
            }
        }
        return delete;
    }

    /**
     * 更新学生信息逻辑：
     * 如果缓存存在，删除
     * 如果缓存不存在，不操作
     */
    @Override
    public int update(Student student) throws InterruptedException {

        /*
           延时双删逻辑
         */
        String key = "student_" + student.getNumbercode();
        Boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
        }
        int update = studentMapper.update(student);
        Thread.sleep(500);
        if( update > 0 ){
            // 缓存存在，进行删除
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("StudentServiceImpl.update() : 从缓存中删除学生 >> " + student.toString());
            }

        }
        return update;
    }

    @Override
    public int insert(Student student) {

        String key = "student_list";

        int insert = studentMapper.insert(student);
        if (insert > 0) {
            redisTemplate.delete(key);
        }
        return insert;
    }
}
