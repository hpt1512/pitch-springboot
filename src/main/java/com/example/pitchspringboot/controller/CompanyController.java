package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.*;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.CommentServiceImpl;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    IBaseService<Location> locationService;
    @Autowired
    IBaseService<Pitch> pitchService;
    @Autowired
    IBaseService<User> userService;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    HttpSession session;
    @GetMapping("")
    public String displayPage(Model model) {
        model.addAttribute("locationList", locationService.findAll());
        model.addAttribute("companyList", companyService.findAll());
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        return "company/list";
    }
    @GetMapping("{id}/pitch")
    public String viewPitchInCompany(Model model, @PathVariable int id) {
        Company company = companyService.findById(id);
        System.out.println(company.getId());
        List<Location> locationList = locationService.findAll();
        List<Pitch> pitchList = pitchService.findAll();
        List<User>  userList = userService.findAll();

        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("company", company);
        model.addAttribute("locationList", locationList);
        model.addAttribute("pitchList", pitchList);
        model.addAttribute("userList", userList);

        List<Company> companyAroundList = companyService.findByLocation(company.getLocation());
        companyAroundList.remove(company);

        if (companyAroundList.size() > 0) {
            Company companyAroundActive = companyAroundList.get(0);
            model.addAttribute("companyAroundActive", companyAroundActive);
            companyAroundList.remove(companyAroundActive);
        }

        model.addAttribute("companyAroundList", companyAroundList);

        Comment comment = new Comment();
        comment.setCompany(company);
        comment.setUser(userSession);
        comment.setLikes(0);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        comment.setDateTimeCreated(formatDateTime); //đặt thời gian bình luận

        model.addAttribute("comment", comment);

        List<Comment> commentList = commentService.findCommentByCompany(company);
        model.addAttribute("commentList", commentList);
        model.addAttribute("countComment", commentService.countCommentByCompany(company));


        return "pitch/list";
    }
    @GetMapping("/findByName")
    public String findCompaniesByName(Model model, @RequestParam("nameFind") String nameFind) {
        if ("".equals(nameFind)) {
            return "redirect:/company";
        }
        model.addAttribute("companyList", companyService.findByName(nameFind));
        model.addAttribute("locationList", locationService.findAll());
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        return "company/list";
    }
    @GetMapping("/findByLocation")
    public String findByLocation(Model model, @RequestParam("location_id") int location_id) {
        Location location = locationService.findById(location_id);
        model.addAttribute("companyList", companyService.findByLocation(location));
        model.addAttribute("locationList", locationService.findAll());
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        return "company/list";
    }
}
