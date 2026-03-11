package com.example.crud.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session){

        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }

        return "dashboard";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(HttpSession session){

        if(session.getAttribute("user")==null){
            return "redirect:/login";
        }

        return "registration";
    }
}
