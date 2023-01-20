package com.example.pitchspringboot.restcontroller;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.IReportIncomeByMonth;
import com.example.pitchspringboot.repositoty.IRepostCompany;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("api/owner")
public class OwnerReportIncome {
    @Autowired
    BookingServiceImpl bookingService;
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    HttpSession session;
    public Company getMyCompany() {
        User userSession = (User) session.getAttribute("user");
        return companyService.findCompanyByUser(userSession);
    }
    @GetMapping("/report-income")
    public ResponseEntity<List<IReportIncomeByMonth>> reportCompanyList() {
        return new ResponseEntity<>(bookingService.reportIncomeByMonth(getMyCompany().getId()), HttpStatus.OK);
    }
}
