package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Comment;
import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.CommentServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    @Autowired
    CommentServiceImpl commentService;
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
        model.addAttribute("mess", "Đã cập nhật thông tin");

        return "user/info";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        return "user/change-password";
    }

    @PostMapping("/comment")
    public String comment(@ModelAttribute("comment")Comment comment, RedirectAttributes redirectAttributes) {
       if ("".equals(comment.getContent().trim())) {
           redirectAttributes.addFlashAttribute("errCmt","Viết đánh giá vào ô trên");
           String id = String.valueOf(comment.getCompany().getId());
           return "redirect:/company/" + id + "/pitch";
       }
        commentService.insert(comment);
        String id = String.valueOf(comment.getCompany().getId());
        return "redirect:/company/" + id + "/pitch";
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id) {
        Comment comment = commentService.findById(id);
        commentService.delete(comment);
        String idCompany = String.valueOf(comment.getCompany().getId());
        return "redirect:/company/" + idCompany + "/pitch";
    }

    @PostMapping("/comment/update")
    public String updateComment(@RequestParam Integer comment_id, @RequestParam String comment_content) {
        commentService.updateContentById(comment_content, comment_id);
        Comment comment = commentService.findById(comment_id);
        String idCompany = String.valueOf(comment.getCompany().getId());
        return "redirect:/company/" + idCompany + "/pitch";
    }

    @GetMapping("/view-user/{id}")
    public String viewUser(Model model, @PathVariable("id") Integer id) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("userView", userService.findById(id));
        return "user/view-user";
    }
}
