package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.*;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    IBaseService<Role> roleService;
    @Autowired
    IBaseService<Location> locationService;
    @Autowired
    HttpSession session;
    @GetMapping("/user")
    public String listUser(Model model) {
        User userSession = (User) session.getAttribute("user");

        // CHeck login
        if (userSession == null) {
            return "redirect:/err-not-login";
        }
        // Check role admin
        if (userSession.getRole().getId() != 1) {
            model.addAttribute("user", userSession);
            return "errors/not_admin";
        }

        model.addAttribute("user", userSession);
        model.addAttribute("userList", userService.findAll());
        return "admin/user/list";
    }

    @GetMapping("/user/create")
    public String createUserPage(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.findAll());
        return "admin/user/create";
    }

    @PostMapping("/user/doCreate")
    public String doCreateUser(@ModelAttribute("newUser") User newUser, RedirectAttributes redirectAttributes) {
        userService.insert(newUser);
        redirectAttributes.addFlashAttribute("mess", "Thêm mới thành công");
        return "redirect:/admin/user";
    }

    @GetMapping("user/edit/{id}")
    public String editUserPage(@PathVariable("id") Integer id, Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("currentUser", userService.findById(id));
        model.addAttribute("roleList", roleService.findAll());
        return "admin/user/edit";
    }

    @PostMapping("/user/doEdit")
    public String doEditUser(@ModelAttribute("currentUser") User currentUser, RedirectAttributes redirectAttributes) {
        userService.update(currentUser);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete")
    public String deleteUser(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        User user = userService.findById(id);
        userService.delete(user);
        redirectAttributes.addFlashAttribute("mess", "Xoá người dùng thành công");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/find")
    public String findUserByName(@RequestParam("input_find") String input_find, Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        if ("".equals(input_find)) {
            return "redirect:/admin/user";
        }
        List<User> userList = userService.findByName(input_find);
        model.addAttribute("userList", userList);
        return "admin/user/list";
    }

    @GetMapping("/company")
    public String listCompany(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("companyList", companyService.findAll());
        return "admin/company/list";
    }

    @GetMapping("/company/create")
    public String createCompanyPage(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        model.addAttribute("newCompany", new Company());
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("locationList", locationService.findAll());
        return "admin/company/create";
    }

    @PostMapping("/company/doCreate")
    public String doCreateCompany(@ModelAttribute("newCompany") Company newCompany, RedirectAttributes redirectAttributes) {
        companyService.insert(newCompany);
        redirectAttributes.addFlashAttribute("mess", "Thêm mới thành công");
        return "redirect:/admin/company";
    }

    @GetMapping("company/edit/{id}")
    public String editCompanyPage(@PathVariable("id") Integer id, Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("currentCompany", companyService.findById(id));
        model.addAttribute("locationList", locationService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "admin/company/edit";
    }

    @PostMapping("/company/doEdit")
    public String doEditCompany(@ModelAttribute("currentCompany") Company currentCompany, RedirectAttributes redirectAttributes) {
        companyService.update(currentCompany);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công");
        return "redirect:/admin/company";
    }

    @GetMapping("/company/delete")
    public String deleteCompany(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        Company company = companyService.findById(id);
        companyService.delete(company);
        redirectAttributes.addFlashAttribute("mess", "Xoá công ty thành công");
        return "redirect:/admin/company";
    }

    @GetMapping("/booking")
    public String listBooking(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("bookingList", bookingService.findAll());
        return "admin/booking/list";
    }

    @GetMapping("/booking/delete")
    public String deleteBooking(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id);
        bookingService.delete(booking);
        redirectAttributes.addFlashAttribute("mess", "Xoá đơn đặt sân thành công");
        return "redirect:/admin/booking";
    }
}
