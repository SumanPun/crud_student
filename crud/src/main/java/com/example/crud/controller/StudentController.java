package com.example.crud.controller;

import com.example.crud.dto.StudentDto;
import com.example.crud.entity.Student;
import com.example.crud.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/create")
    public String createStudentForm(Model model){
        model.addAttribute("student", new Student());
        return "students/create";
    }

    @PostMapping("/create")
    public String CreateStudent(@ModelAttribute StudentDto student, BindingResult result){
        if(result.hasErrors()){
            return "students/create";
        }
        this.studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Integer id, Model model){
        StudentDto student = studentService.getStudentById(id);
        if(student != null){
            model.addAttribute("student", student);
            return "students/edit";
        }
        return "redirect:/students";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Integer id, @ModelAttribute StudentDto student, BindingResult result){
        if(result.hasErrors()){
            return "students/edit";
        }
        student.setId(id);
        studentService.updateStudent(id,student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
