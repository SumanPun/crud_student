package com.example.crud.services;

import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;

import java.util.List;


public interface StudentService {

    List<Student> getAllStudents();
    Student getStudentById(Integer id);
    void saveStudent(Student student);
    Student updateStudent(Integer id, Student student);
    void deleteStudent(Integer id);
}
