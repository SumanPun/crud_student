package com.example.crud.controller;

import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import com.example.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String viewProfile(@RequestParam String username, Model model){
        UserDto user = userService.findUserByUsername(username);
        if (user == null){
            return "redirect:/user";
        }
        model.addAttribute("user",user);
        return "profile";
    }
}
