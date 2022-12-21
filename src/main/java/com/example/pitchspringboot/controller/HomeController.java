package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Login;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    IBaseService<User> userService;
    @Autowired
    HttpSession httpSession;
    @GetMapping("")
    public String home() {
        return "home";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }
    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("login") Login login, Model model) {
        List<User> userList = userService.findAll();
        for (User user : userList) {
            if (user.getUsername().equals(login.getUsername()) && user.getPassword().equals(login.getPassword())) {
                httpSession.setAttribute("user", user);
                model.addAttribute("mess", "Login suscessfully - Hello " + user.getFullName());
                break;
            }
        }
        return "login";
    }
}
