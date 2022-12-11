package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Location;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    IBaseService<Company> companyService;
    @Autowired
    IBaseService<Location> locationService;
    @Autowired
    IBaseService<Pitch> pitchService;
    @Autowired
    IBaseService<User> userService;
    @GetMapping("")
    public String displayPage(Model model) {
        model.addAttribute("locationList", locationService.findAll());
        model.addAttribute("companyList", companyService.findAll());
        return "company/list";
    }
    @GetMapping("{id}/pitch")
    public String viewPitchInCompany(Model model, @PathVariable int id) {
        Company company = companyService.findById(id);
        System.out.println(company.getId());
        List<Location> locationList = locationService.findAll();
        List<Pitch> pitchList = pitchService.findAll();
        List<User>  userList = userService.findAll();

        model.addAttribute("company", company);
        model.addAttribute("locationList", locationList);
        model.addAttribute("pitchList", pitchList);
        model.addAttribute("userList", userList);
        return "pitch/list";
    }
}
