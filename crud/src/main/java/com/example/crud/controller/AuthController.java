package com.example.crud.controller;

import com.example.crud.dto.LoginDto;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import com.example.crud.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static java.rmi.server.LogStream.log;

@Slf4j
@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/login")
    public String loginForm(Model model){
        LoginDto login_user = new LoginDto();
        model.addAttribute("user-login",login_user);
        return "login";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user-login") LoginDto loginDto,
                               BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("user-login", loginDto);
            return "login";
        }

        User existingUser = userService.findUserByEmail(loginDto.getEmail());

        if(existingUser == null){
//            result.rejectValue("email",null,"" +
//                    loginDto.getEmail()+" not found !!");
            return "login";
        }
        boolean check = passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword());
        if(check == false){
            throw new BadCredentialsException("Invalid Username and Password");
        }
        return "redirect:/students/index";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result, Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null){
            result.rejectValue("email",null,"" +
                    "User already registered !!");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/registration";
        }

        userService.saveUser(userDto);
        return "redirect:/registration?success";
    }
}
