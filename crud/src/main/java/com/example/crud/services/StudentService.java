package com.example.crud.services;

import com.example.crud.dto.StudentDto;


import java.util.List;


public interface StudentService {

    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Integer id);
    StudentDto saveStudent(StudentDto dto);
    StudentDto updateStudent(Integer id, StudentDto student);
    void deleteStudent(Integer id);
}
