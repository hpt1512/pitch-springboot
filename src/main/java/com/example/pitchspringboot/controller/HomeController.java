package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Login;
import com.example.pitchspringboot.model.Role;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.ITopCompany;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import com.example.pitchspringboot.validate.UserValidate;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    IBaseService<User> userService;
    @Autowired
    HttpSession session;
    @Autowired
    IBaseService<Role> roleService;
    @Autowired
    UserValidate userValidate;
    @Autowired
    CompanyServiceImpl companyService;
    @GetMapping("/")
    public String home(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        List<ITopCompany> topCompanies = companyService.getTopCompany();
        model.addAttribute("topCompanies", topCompanies);
        List<Company> companyListTop = new ArrayList<>();
        for (ITopCompany company : topCompanies) {
            System.out.println(company.getId());
            companyListTop.add(companyService.findById(company.getId()));
        }
        model.addAttribute("companyListTop", companyListTop);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("login") Login login, Model model,
                          HttpSession session) {
        List<User> userList = userService.findAll();
        for (User user : userList) {
            if (user.getUsername().equals(login.getUsername())
                    && user.getPassword().equals(login.getPassword())) {
                if (user.getStatus() == 1) {
                    session.setAttribute("user", user);
                    return "redirect:";
                } else {
                    model.addAttribute("error", "Tài khoản này chưa được kích hoạt, vui lòng đợi");
                    return "login";
                }
            }
        }
        model.addAttribute("error", "Đăng nhập thất bại. Tài khoản hoặc mật khẩu không chính xác");
        return "login";
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        User newUser = new User();

        newUser.setImage(null);
        newUser.setPoint(0);
        newUser.setStatus(0);
        newUser.setRole(roleService.findById(2));

        model.addAttribute("newUser", newUser);
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("newUser") User newUser, BindingResult bindingResult, @RequestParam("copyPassword") String copyPassword,
                             RedirectAttributes redirectAttributes, Model model) {
        if (!newUser.getPassword().equals(copyPassword)) {
            model.addAttribute("errPassword", "Hai mật khẩu không khớp nhau, vui lòng kiểm tra lại");
            return "register";
        }
        userValidate.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.insert(newUser);
        redirectAttributes.addFlashAttribute("mess", "Đăng ký thành công, vui lòng đợi quản trị viên kích hoạt tài khoản để sử dụng dịch vụ");
        return "redirect:/login";
    }
}
