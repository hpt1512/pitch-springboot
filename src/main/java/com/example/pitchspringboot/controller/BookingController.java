package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.*;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.validate.BookingValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    IBaseService<Pitch> pitchService;
    @Autowired
    IBaseService<Booking> bookingService;
    @Autowired
    IBaseService<Voucher> voucherService;
    @Autowired
    IBaseService<Company> companyService;
    @Autowired
    IBaseService<User> userService;
    @Autowired
    HttpSession session;

    @Autowired
    BookingValidate bookingValidate;

    @GetMapping("/create/{id}")
    public String showFormBooking(Model model, @PathVariable int id) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        User user = userService.findById(userSession.getId());
        session.setAttribute("user", user);

        if (userSession == null) {
            return "redirect:/err-not-login";
        }

        Pitch pitch =  pitchService.findById(id);
        Company company = companyService.findById(pitch.getCompany().getId());

        Booking booking = new Booking();
        booking.setPitch(pitch); //đặt pitch mặc định cho booking

        booking.setUser(userSession); //đặt user mặc định cho booking là user đang đăng nhập

        booking.setStatus(0); //đặt status mặc định

        //đặt voucher mặc định - Lấy user đã update point từ database
        Voucher voucher = new Voucher();
        if (user.getPoint() % 5 == 0 && user.getPoint() != 0) {
            voucher = voucherService.findById(2);
        } else {
            voucher = voucherService.findById(1);
        }
        booking.setVoucher(voucher);

        booking.setPrice(pitch.getPrice() - voucher.getBonus()); //đặt giá mặc định


        List<String> timeList = Arrays.asList("15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
        model.addAttribute("timeList", timeList);

        model.addAttribute("pitch", pitch);
        model.addAttribute("booking", booking);
        model.addAttribute("company", company);

        return "booking/create";
    }
    @PostMapping("/create")
    public String createBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult,
                                Model model) {
        bookingValidate.validate(booking, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> timeList = Arrays.asList("15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
            model.addAttribute("timeList", timeList);
            model.addAttribute("pitch", booking.getPitch());
            Company company = companyService.findById(booking.getPitch().getCompany().getId());
//            System.out.println(company.getName());
            model.addAttribute("company", company);
            User userSession = (User) session.getAttribute("user");
            model.addAttribute("user", userSession);
            return "booking/create";
        }
        bookingService.insert(booking);
        return "redirect:/company";
    }
}
