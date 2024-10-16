package com.example.crud.controller;

import com.example.crud.dto.StudentDto;
import com.example.crud.entity.Student;
import com.example.crud.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        return "students/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createStudentForm(Model model){
        model.addAttribute("student", new Student());
        return "students/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ModelAndView CreateStudent(@Valid @ModelAttribute("student") StudentDto student, BindingResult result){
        if(result.hasErrors()){
            // Log the errors to debug
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            ModelAndView modelAndView = new ModelAndView("/students/create");
            modelAndView.addObject("student",student);
            return modelAndView;
        }
        this.studentService.saveStudent(student);
        return new ModelAndView("redirect:/students");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Integer id, Model model){
        StudentDto student = studentService.getStudentById(id);
        if(student != null){
            model.addAttribute("student", student);
            return "students/edit";
        }
        return "error";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit/{id}")
    public ModelAndView updateStudent(@PathVariable Integer id, @Valid @ModelAttribute("student") StudentDto student, BindingResult result){
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            ModelAndView modelAndView = new ModelAndView("/students/edit");
            modelAndView.addObject("student",student);
            return modelAndView;
        }
        student.setId(id);
        studentService.updateStudent(id,student);
        return new ModelAndView("redirect:/students");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id){
        StudentDto student = studentService.getStudentById(id);
        if(student != null){
            studentService.deleteStudent(id);
            return "redirect:/students";
        }
        return "error";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam ("keywords") String keywords, Model model){
        List<StudentDto> students = studentService.searchStudent("%" + keywords + "%");
        model.addAttribute("students",students);
        return "students/index";
    }
}
