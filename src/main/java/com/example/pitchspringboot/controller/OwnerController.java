package com.example.pitchspringboot.controller;

import com.example.pitchspringboot.model.*;
import com.example.pitchspringboot.repositoty.IBookingRepository;
import com.example.pitchspringboot.repositoty.IReportIncomeByMonth;
import com.example.pitchspringboot.service.IBaseService;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import com.example.pitchspringboot.service.impl.PitchServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import com.example.pitchspringboot.validate.CompanyEditValidate;
import com.example.pitchspringboot.validate.PitchValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    BookingServiceImpl bookingService;
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    HttpSession session;
    @Autowired
    PitchServiceImpl pitchService;
    @Autowired
    IBaseService<Location> locationService;
    @Autowired
    PitchValidate pitchValidate;
    @Autowired
    CompanyEditValidate companyEditValidate;
    private List<String> timeList = Arrays.asList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");


    public Company getMyCompany() {
        User userSession = (User) session.getAttribute("user");
        return companyService.findCompanyByUser(userSession);
    }

    @GetMapping("/myCompany")
    public String myCompanyManage(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);

        Company company = getMyCompany();

        if (userSession.getRole().getId() != 3) {
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
                              @RequestParam("statusFind") String statusFind,
                              Model model) {
        if ("".equals(pitchFindId) && "".equals(datePlay) && "".equals(timePlay) && "".equals(statusFind)) {
            return "redirect:/owner/booking";
        }
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        List<Booking> bookings = bookingService.findByPitchDateTimeCustoms(pitchFindId, datePlay, timePlay, statusFind);
        List<Booking> bookingList = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getPitch().getCompany().getId() == getMyCompany().getId()) {
                bookingList.add(booking);
            }
        }
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("timeList", timeList);
        model.addAttribute("pitchList", pitchService.findByCompany(getMyCompany()));
        return "owner/booking/list";
    }

    @GetMapping("/booking/viewUser/{id}")
    public String viewUser(@PathVariable("id") Integer id, Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        model.addAttribute("userView", userService.findById(id));
        return "owner/booking/view-user";
    }

    @GetMapping("/myCompany/info")
    public String infoMyCompany(Model model) {
        model.addAttribute("company", getMyCompany());
        model.addAttribute("locationList", locationService.findAll());
        return "owner/company/info";
    }

    @PostMapping("/myCompany/editInfo")
    public String doEditCompany(@Valid @ModelAttribute("company") Company company, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        companyEditValidate.validate(company, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("locationList", locationService.findAll());
            User userSession = (User) session.getAttribute("user");
            model.addAttribute("user", userSession);
            return "owner/company/info";
        }
        companyService.update(company);
        redirectAttributes.addFlashAttribute("messInfo", "Đã cập nhật thông tin sân bóng");
        return "redirect:/owner/myCompany";
    }

    @PostMapping("/myCompany/create")
    public String doCreatePitch(@Valid @ModelAttribute("newPitch") Pitch newPitch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        pitchValidate.validate(newPitch, bindingResult);
        if (bindingResult.hasErrors()) {
            Company company = getMyCompany();
            model.addAttribute("company", company);
            model.addAttribute("pitchList", pitchService.findByCompany(company));
            model.addAttribute("checkBlock", 1);
            User userSession = (User) session.getAttribute("user");
            model.addAttribute("user", userSession);
            return "owner/company/list";
        }
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
    public String đoEitPitch(@Valid @ModelAttribute("pitch") Pitch pitch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        pitchValidate.validate(pitch, bindingResult);
        if (bindingResult.hasErrors()) {
            Company company = getMyCompany();
            model.addAttribute("company", company);
            User userSession = (User) session.getAttribute("user");
            model.addAttribute("user", userSession);
            return "owner/company/edit";
        }
        pitchService.update(pitch);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật sân bóng thành công");
        return "redirect:/owner/myCompany";
    }

    @GetMapping("/myCompany/delete")
    public String deletePitch(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Pitch pitch = pitchService.findById(id);

        List<Booking> bookingList = bookingService.findByPitch(pitch);
        if (bookingList.size() != 0) {
            for (Booking booking : bookingList) {
                bookingService.delete(booking);
            }
        }
        pitchService.delete(pitch);
        redirectAttributes.addFlashAttribute("mess", "Xoá sân bóng thành công");
        return "redirect:/owner/myCompany";
    }

    @GetMapping("/report-income")
    public String reportIncoome(Model model) {
        User userSession = (User) session.getAttribute("user");
        model.addAttribute("user", userSession);
        Company company = getMyCompany();
        List<IReportIncomeByMonth> reportIncomeList = bookingService.reportIncomeByMonth(company.getId());
        model.addAttribute("reportIncomeList", reportIncomeList);

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer currentMonth = c.MONTH;
        Integer currentMonthIncome = 0;
        for (IReportIncomeByMonth ob:reportIncomeList) {
            if (ob.getMonth() == currentMonth) {
                currentMonthIncome = ob.getSum();
            }
        }
        model.addAttribute("currentMonthIncome", currentMonthIncome);
        return "owner/report-income/report-income";
    }
}
