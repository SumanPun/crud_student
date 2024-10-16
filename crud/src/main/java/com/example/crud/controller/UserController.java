package com.example.crud.controller;

import com.example.crud.dto.EditUserDto;
import com.example.crud.dto.StudentDto;
import com.example.crud.dto.UserDto;
import com.example.crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userForm(){
        return "user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String usersList(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "users/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        EditUserDto user = userService.getEditUserById(id);
        if(user != null){
            model.addAttribute("user", user);
            return "users/edit";
        }
        return "error";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/edit/{id}")
    public ModelAndView updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") EditUserDto user, BindingResult result){
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            ModelAndView modelAndView = new ModelAndView("/users/edit");
            modelAndView.addObject("user",user);
            return modelAndView;
        }
        user.setId(id);
        userService.editUser(id,user);
        return new ModelAndView("redirect:/users");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        if(user != null){
            userService.deleteUserById(id);
            return "redirect:/users";
        }
        return "error";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(){
        return "profile";
    }
}
