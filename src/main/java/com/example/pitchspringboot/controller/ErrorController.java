package com.example.pitchspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/err-not-admin")
    public String errorNotAdmin() {
        return "errors/not_admin";
    }
    @GetMapping("/err-not-login")
    public String errorNotLogin() {
        return "errors/not_login";
    }
    @GetMapping("/err-not-owner")
    public String errorNotOwner() {
        return "errors/not_owner";
    }
}
