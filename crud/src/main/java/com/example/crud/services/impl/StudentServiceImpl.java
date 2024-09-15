package com.example.crud.services.impl;


import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;
import com.example.crud.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return this.studentRepository.findAll();
    }

    public Student getStudentById(Integer id){
        return this.studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Student not found: "+id));
    }

    public void saveStudent(Student student){
        studentRepository.save(student);
    }

    public Student updateStudent(Integer id, Student student){
        Student getStudent = studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Student not found"+id));
        getStudent.setAge(student.getAge());
        getStudent.setAddress(student.getAddress());
        getStudent.setEmail(student.getEmail());
        getStudent.setFullName(student.getFullName());
        studentRepository.save(student);
        return getStudent;
    }

    public void deleteStudent(Integer id){
        this.studentRepository.deleteById(id);
    }
}

