package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentDto {
    private Integer id;
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please enter valid email")
    private String email;
    @NotNull(message = "Name cannot be empty")
    @Size(min = 3, max = 10, message = "Please enter your name between 3 and 10 characters")
    private String fullName;
    @NotNull(message = "Age cannot be empty")
    private Integer age;
    @NotNull(message = "Address cannot be empty")
    @Size(min = 3, max = 10, message = "Please enter your address between 3 and 10 character")
    private String address;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public void setFullName(String name){
        this.fullName = name;
    }
    public String getFullName(){
        return fullName;
    }
    public void setAge(Integer age){
        this.age = age;
    }
    public Integer getAge(){
        return age;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return address;
    }
}
