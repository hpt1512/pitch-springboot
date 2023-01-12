package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.IBookingRepository;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.PitchServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    BookingServiceImpl bookingService;
    @Autowired
    IBaseService<Company> companyService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    HttpSession session;
    @Autowired
    PitchServiceImpl pitchService;

    public Company getMyCompany() {
        User userSession = (User) session.getAttribute("user");
        Company company = null;
        List<Company> companyList = companyService.findAll();
        for (Company c : companyList) {
            if (c.getUser().getId() == userSession.getId()) {
                company = c;
            }
        }
        return company;
    }

    @GetMapping("/myCompany")
    public String myCompanyManage(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        Company company = getMyCompany();

        if (company == null) {
            return "errors/not_owner";
        }

        List<Pitch> pitchList = pitchService.findByCompany(company);

        model.addAttribute("company", company);
        model.addAttribute("pitchList", pitchList);


        Pitch pitch = new Pitch();
        pitch.setStatus(0);
        pitch.setCompany(company);

        model.addAttribute("newPitch", pitch);


        return "owner/company/list";
    }

    @GetMapping("/booking")
    public String bookingManage(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        Company company = getMyCompany();


        List<Booking> bookingList = new ArrayList<>();
        List<Booking> allBookingList = bookingService.findAll();

        for (Booking b : allBookingList) {
            if (b.getPitch().getCompany().getId() == company.getId()) {
                bookingList.add(b);
            }
        }

        model.addAttribute("bookingList", bookingList);
        List<String> timeList = Arrays.asList("15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
        model.addAttribute("timeList", timeList);
        model.addAttribute("pitchList", pitchService.findByCompany(company));
        return "owner/booking/list";
    }

    @GetMapping("/confirmBooking/{id}")
    public String confirmBooking(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        bookingService.confirmBooking(id);
        redirectAttributes.addFlashAttribute("messConfirm", "Đã xác nhận đơn đặt sân");
        return "redirect:/owner/booking";
    }

    @GetMapping("/declineBooking/{id}")
    public String declineBooking(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        bookingService.declineBooking(id);
        redirectAttributes.addFlashAttribute("messDecline", "Đã từ chối đơn đặt sân");
        return "redirect:/owner/booking";
    }

    @GetMapping("/confirmPay/{id}")
    public String confirmPay(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        bookingService.confirmPay(id);
        Booking booking = bookingService.findById(id);
        userService.raisePoint(booking.getUser().getId());
        redirectAttributes.addFlashAttribute("messConfirmPay", "Đã xác nhận thanh toán đơn đặt sân");
        return "redirect:/owner/booking";
    }

    @GetMapping("/booking/delete/{id}")
    public String deleteBooking(Model model, @PathVariable("id") Integer id) {
        Booking booking = bookingService.findById(id);
        bookingService.delete(booking);
        return "redirect:/owner/booking";
    }

    @GetMapping("/booking/find")
    public String findBooking(@RequestParam("pitchName") String pitchFindId,
                              @RequestParam("datePlay") String datePlay,
                              @RequestParam("timePlay") String timePlay,
                              Model model) {
        if ("".equals(pitchFindId) && "".equals(datePlay) && "".equals(timePlay)) {
            return "redirect:/owner/booking";
        }
        List<Booking> bookingList = bookingService.findByPitchDateTimeCustoms(pitchFindId, datePlay, timePlay);
        model.addAttribute("bookingList", bookingList);
        List<String> timeList = Arrays.asList("15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
        model.addAttribute("timeList", timeList);
        model.addAttribute("pitchList", pitchService.findByCompany(getMyCompany()));
        return "owner/booking/list";
    }

    @PostMapping("/myCompany/create")
    public String doCreatePitch(@ModelAttribute("newPitch") Pitch newPitch, RedirectAttributes redirectAttributes) {
        pitchService.insert(newPitch);
        redirectAttributes.addFlashAttribute("mess", "Thêm mới sân bóng thành công");
        return "redirect:/owner/myCompany";
    }

    @GetMapping("myCompany/edit/{id}")
    public String showFormEdit(@PathVariable("id") Integer id, Model model) {
        Company company = getMyCompany();
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("pitch", pitchService.findById(id));
        model.addAttribute("company", company);
        return "owner/company/edit";
    }

    @PostMapping("/myCompany/doEdit")
    public String đoEitPitch(@ModelAttribute("pitch") Pitch pitch, RedirectAttributes redirectAttributes) {
        pitchService.update(pitch);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật sân bóng thành công");
        return "redirect:/owner/myCompany";
    }

    @GetMapping("/myCompany/delete")
    public String deletePitch(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Pitch pitch = pitchService.findById(id);
        pitchService.delete(pitch);
        redirectAttributes.addFlashAttribute("mess", "Xoá sân bóng thành công");
        return "redirect:/owner/myCompany";
    }
}
