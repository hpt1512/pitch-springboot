package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    BookingServiceImpl bookingService;
    @GetMapping("/myBooking")
    public String myBooking(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        List<Booking> bookingList = bookingService.findByUser(userSession);
        model.addAttribute("bookingList", bookingList);

        return "user/mybooking";
    }

    @GetMapping("/booking/delete")
    public String deleteBooking(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id);
        bookingService.delete(booking);
        redirectAttributes.addFlashAttribute("mess", "Đã huỷ đơn đặt sân");
        return "redirect:/user/myBooking";
    }

    @GetMapping("/info")
    public String info(Model model) {
        User userSession = (User) session.getAttribute("user");
        User user = userService.findById(userSession.getId());
        model.addAttribute("user", user);
        return "user/info";
    }

    @PostMapping("/edit")
    public String updateInfo(Model model, @ModelAttribute("user") User user) {
        User userSession = (User) session.getAttribute("user");

        userService.update(user);

        User userUpdated = userService.findById(userSession.getId());
        session.setAttribute("user", userUpdated);

        User userSessionUpdated = (User) session.getAttribute("user");
        model.addAttribute("user", userSessionUpdated);

        return "user/info";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        return "user/change-password";
    }

//    @PostMapping("/changePassword")
//    public String changePassword(Model model, @ModelAttribute("user") User user,
//                                 @RequestParam("oldPassword") String oldPassword,
//                                 @RequestParam("newCopyPassword") String newCopyPassword) {
//        User userSession = (User) session.getAttribute("user");
//
//        if (!oldPassword.equals(userSession.getPassword())) {
//            model.addAttribute("mess", "Mật khẩu cũ không đúng");
//        } else if (!newCopyPassword.equals(user.getPassword())) {
//            model.addAttribute("mess", "Hai mật khẩu không khớp nhau, vui lòng kiểm tra lại");
//        } else {
//            userService.update(user);
//            User userUpdated = userService.findById(userSession.getId());
//            session.setAttribute("user", userUpdated);
//
//            User userSessionUpdated = (User) session.getAttribute("user");
//            model.addAttribute("user", userSessionUpdated);
//        }
//        return "user/change-password";
//    }

}
