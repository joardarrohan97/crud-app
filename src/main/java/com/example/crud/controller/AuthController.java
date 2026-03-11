package com.example.crud.controller;

import com.example.crud.model.AppUser;
import com.example.crud.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public String register(@RequestBody AppUser user){
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "User saved";
    }

    @PostMapping("/login")
    public AppUser login(@RequestBody AppUser user, HttpSession session){

        AppUser db = repo.findByUsername(user.getUsername()).orElseThrow();

        if(encoder.matches(user.getPassword(), db.getPassword())){
            session.setAttribute("user", db.getUsername());
            db.setPassword(null);
            return db;
        }

        throw new RuntimeException("Invalid credentials");
    }

    @PostMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
