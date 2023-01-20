package com.example.pitchspringboot.restcontroller;

import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.IRepostCompany;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class ReportRestController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CompanyServiceImpl companyService;
    @GetMapping("/report-user")
    public ResponseEntity<List<User>> reportUserList() {
        return new ResponseEntity<>(userService.reportUser(), HttpStatus.OK);
    }
    @GetMapping("/report-company")
    public ResponseEntity<List<IRepostCompany>> reportCompanyList() {
        return new ResponseEntity<>(companyService.getReportCompany(), HttpStatus.OK);
    }

}
