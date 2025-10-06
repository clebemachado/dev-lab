package com.dev.spring_security_basics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/public/my-page")
    public String myPage() {
        return "My Page";
    }

    @GetMapping("/admin/dashboard")
    public String myDashboard(){
        return "My DashBoard";
    }
}
