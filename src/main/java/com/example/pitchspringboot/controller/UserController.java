package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Comment;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.CommentServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import com.example.pitchspringboot.validate.UserEditValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    UserEditValidate userEditValidate;
    private List<String> timeList = Arrays.asList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
    @GetMapping("/myBooking")
    public String myBooking(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        List<Booking> bookingList = bookingService.findByUser(userSession);
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("timeList", timeList);

        return "user/mybooking";
    }

    @GetMapping("/booking/delete")
    public String deleteBooking(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.findById(id);
        bookingService.delete(booking);
        redirectAttributes.addFlashAttribute("mess", "Đã huỷ đơn đặt sân");
        return "redirect:/user/myBooking";
    }

    @GetMapping("/mybooking/find")
    public String findBooking(@RequestParam("pitchName") String pitchFindId,
                              @RequestParam("datePlay") String datePlay,
                              @RequestParam("timePlay") String timePlay,
                              @RequestParam("statusFind") String statusFind,
                              Model model) {
        if ("".equals(pitchFindId) && "".equals(datePlay) && "".equals(timePlay) && "".equals(statusFind)) {
            return "redirect:/user/myBooking";
        }
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        List<Booking> bookings = bookingService.findByPitchDateTimeCustoms(pitchFindId, datePlay, timePlay, statusFind);
        List<Booking> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().getId() == userSession.getId()) {
                bookingList.add(booking);
            }
        }
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("timeList", timeList);
        return "user/mybooking";
    }

    @GetMapping("/info")
    public String info(Model model) {
        User userSession = (User) session.getAttribute("user");
        User user = userService.findById(userSession.getId());
        model.addAttribute("user", user);
        return "user/info";
    }

    @PostMapping("/edit")
    public String updateInfo(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        User userSession = (User) session.getAttribute("user");

        userEditValidate.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userSession);
            return "user/info";
        }

        userService.update(user);

        User userUpdated = userService.findById(userSession.getId());
        session.setAttribute("user", userUpdated);

        User userSessionUpdated = (User) session.getAttribute("user");
        model.addAttribute("user", userSessionUpdated);
        model.addAttribute("mess", "Đã cập nhật thông tin");

        return "user/info";
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
    public String updateComment(@RequestParam Integer comment_id, @RequestParam String comment_content, RedirectAttributes redirectAttributes) {
        if ("".equals(comment_content.trim())) {
            redirectAttributes.addFlashAttribute("errCmt","Bạn chưa viết đánh giá");
            Comment comment = commentService.findById(comment_id);
            String id = String.valueOf(comment.getCompany().getId());
            return "redirect:/company/" + id + "/pitch";
        }
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

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        return "user/change-password";
    }

    @PostMapping("/change-password")
    public String doChangePassword(@RequestParam String newPassword,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPasswordAgain,
                                   Model model) {
        User userSession = (User) session.getAttribute("user");
        userService.changePassword(newPassword, userSession.getId());
        if (!Objects.equals(oldPassword, userSession.getPassword())) {
            model.addAttribute("messErr", "Mật khẩu cũ không đúng");
            return "/user/change-password";
        }
        if (!Objects.equals(newPassword, newPasswordAgain)) {
            model.addAttribute("messErr", "Hai mật khẩu không khớp nhau, vui lòng kiểm tra lại");
            return "/user/change-password";
        }
        if ("".equals(newPassword)) {
            model.addAttribute("messErr", "Nhập mật khẩu mới");
            return "/user/change-password";
        }
        model.addAttribute("mess", "Thay đổi mật khấu thành công");
        User userUpdated = userService.findById(userSession.getId());
        session.setAttribute("user", userUpdated);
        model.addAttribute("user", userSession);
        return "/user/change-password";
    }
}
