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

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IBaseService<User> userService;
    @Autowired
    IBaseService<Company> companyService;
    @Autowired
    IBaseService<Booking> bookingService;
    @GetMapping("/user")
    public String listUser(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "admin/user/list";
    }
    @GetMapping("/company")
    public String listCompany(Model model) {
        model.addAttribute("companyList", companyService.findAll());
        return "admin/company/list";
    }
    @GetMapping("/booking")
    public String listBooking(Model model) {
        model.addAttribute("bookingList", bookingService.findAll());
        return "admin/booking/list";
    }
}
