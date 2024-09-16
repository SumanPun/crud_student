package com.example.crud.services.impl;


import com.example.crud.dto.StudentDto;
import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;
import com.example.crud.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map((this::getStudentDto))
                .collect(Collectors.toList());
    }

    public StudentDto saveStudent(StudentDto dto){
        Student student = this.getStudent(dto);
        this.studentRepository.save(student);
        return getStudentDto(student);
    }

    public StudentDto getStudentById(Integer id){
        Student student = studentRepository.getStudentById(id)
               .orElseThrow(()-> new IllegalArgumentException("Student not found: "+id));
        return getStudentDto(student);
    }

    public StudentDto updateStudent(Integer id, StudentDto student){
        Student getStudent = studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Student not found"+id));
        getStudent.setAge(student.getAge());
        getStudent.setAddress(student.getAddress());
        getStudent.setEmail(student.getEmail());
        getStudent.setFullName(student.getFullName());
        this.studentRepository.save(getStudent);
        return getStudentDto(getStudent);
    }

    public void deleteStudent(Integer id){
        this.studentRepository.deleteById(id);
    }

    private StudentDto getStudentDto(Student student){
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setEmail(student.getEmail());
        dto.setFullName(student.getFullName());
        dto.setAge(student.getAge());
        dto.setAddress(student.getAddress());
        return dto;
    }
    private Student getStudent(StudentDto student){
        Student dto = new Student();
        dto.setId(student.getId());
        dto.setEmail(student.getEmail());
        dto.setFullName(student.getFullName());
        dto.setAge(student.getAge());
        dto.setAddress(student.getAddress());
        return dto;
    }
}

