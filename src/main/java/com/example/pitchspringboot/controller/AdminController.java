package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IBaseService<User> userService;
    @Autowired
    IBaseService<Company> companyService;
    @Autowired
    IBaseService<Booking> bookingService;
    @Autowired
    HttpSession session;
    @GetMapping("/user")
    public String listUser(Model model) {
        User userSession = (User) session.getAttribute("user");

        if (userSession == null) {
            return "redirect:/err-not-login";
        }
        if (userSession.getRole().getId() != 1) {
            model.addAttribute("user", userSession);
            return "errors/not_admin";
        }

        model.addAttribute("user", userSession);
        model.addAttribute("userList", userService.findAll());
        return "admin/user/list";
    }
    @GetMapping("/company")
    public String listCompany(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("companyList", companyService.findAll());
        return "admin/company/list";
    }
    @GetMapping("/booking")
    public String listBooking(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("bookingList", bookingService.findAll());
        return "admin/booking/list";
    }
}
