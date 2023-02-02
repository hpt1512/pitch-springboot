package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.*;
import com.example.pitchspringboot.repositoty.IRepostCompany;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import com.example.pitchspringboot.service.impl.PitchServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import com.example.pitchspringboot.validate.CompanyValidate;
import com.example.pitchspringboot.validate.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    BookingServiceImpl bookingService;
    @Autowired
    IBaseService<Role> roleService;
    @Autowired
    IBaseService<Location> locationService;
    @Autowired
    PitchServiceImpl pitchService;
    @Autowired
    UserValidate userValidate;
    @Autowired
    CompanyValidate companyValidate;
    @Autowired
    HttpSession session;
    private List<String> timeList = Arrays.asList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");

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
        User user = new User();
        user.setPassword("customer");
        user.setPoint(0);
        user.setStatus(1);
        model.addAttribute("newUser", user);
        model.addAttribute("roleList", roleService.findAll());
        return "admin/user/create";
    }

    @PostMapping("/user/doCreate")
    public String doCreateUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        userValidate.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roleList", roleService.findAll());
            return "admin/user/create";
        }
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
        Company company = companyService.findCompanyByUser(user);
        List<Booking> bookingList = bookingService.findByUser(user);
        if (company != null) {
            companyService.delete(company);
        }
        if (bookingList.size() != 0) {
            for (Booking booking : bookingList) {
                bookingService.delete(booking);
            }
        }
        userService.delete(user);
        redirectAttributes.addFlashAttribute("mess", "Xoá người dùng thành công");

        if (user.getStatus() == 1) {
            return "redirect:/admin/user";
        } else {
            return "redirect:/admin/activateAccount";
        }
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

    @GetMapping("/user/findById")
    public String findUserById(@RequestParam("id_find") Integer id_find, Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        if ("".equals(id_find)) {
            return "redirect:/admin/user";
        }
        User user = userService.findById(id_find);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        model.addAttribute("userList", userList);
        return "admin/user/list";
    }

    @GetMapping("/activateAccount")
    public String activateAccount(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("userList", userService.findAll());
        return "admin/user/list-activate";
    }

    @GetMapping("/activateAccount/{id}")
    public String activateAccount(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.activateAccount(id);
        redirectAttributes.addFlashAttribute("mess", "Đã kích hoạt tài khoản");
        return "redirect:/admin/activateAccount";
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

        Company company = new Company();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        company.setDateTimeCreated(formatDateTime); //đặt thời gian tạo sân

        model.addAttribute("newCompany", company);
        model.addAttribute("userList", userService.findAll());
        model.addAttribute("locationList", locationService.findAll());
        return "admin/company/create";
    }

    @PostMapping("/company/doCreate")
    public String doCreateCompany(@Valid @ModelAttribute("newCompany") Company newCompany, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        companyValidate.validate(newCompany, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("locationList", locationService.findAll());
            return "admin/company/create";
        }
        companyService.insert(newCompany);
        userService.addRoleOwner(newCompany.getUser().getId());
        redirectAttributes.addFlashAttribute("mess", "Thêm mới sân bóng thành công");
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
        userService.removeRoleOwner(company.getUser().getId());
        redirectAttributes.addFlashAttribute("mess", "Xoá công ty thành công");
        return "redirect:/admin/company";
    }

    @GetMapping("/booking")
    public String listBooking(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("bookingList", bookingService.findAll());
        model.addAttribute("companyList", companyService.findAll());
        model.addAttribute("timeList", timeList);
        model.addAttribute("pitchList", pitchService.findAll());

        return "admin/booking/list";
    }

    @GetMapping("/booking/delete")
    public String deleteBooking(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id);
        bookingService.delete(booking);
        redirectAttributes.addFlashAttribute("mess", "Xoá đơn đặt sân thành công");
        return "redirect:/admin/booking";
    }

    @GetMapping("/booking/find")
    public String findBooking(@RequestParam("pitchName") String pitchFindId,
                              @RequestParam("datePlay") String datePlay,
                              @RequestParam("timePlay") String timePlay,
                              Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        if ("".equals(pitchFindId) && "".equals(datePlay) && "".equals(timePlay)) {
            return "redirect:/admin/booking";
        }
        List<Booking> bookingList = bookingService.findByPitchDateTimeCustoms(pitchFindId, datePlay, timePlay);
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("timeList", timeList);
        model.addAttribute("pitchList", pitchService.findAll());
        return "admin/booking/list";
    }

    // ------------ THỐNG KÊ ------------
    @GetMapping("/report-user")
    public String userReport(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("countUser", userService.countAllUser());
        model.addAttribute("countCompany", companyService.countAllCompany());
        model.addAttribute("reportUserList", userService.reportUser());
        return "admin/report/user-report";
    }

    @GetMapping("/report-company")
    public String companyReport(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("countUser", userService.countAllUser());
        model.addAttribute("countCompany", companyService.countAllCompany());

        List<IRepostCompany> reportCompanyList = companyService.getReportCompany();
        model.addAttribute("reportCompanyList", reportCompanyList);

        return "admin/report/company-report";
    }
}
