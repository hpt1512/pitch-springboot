package com.example.pitchspringboot.validate;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CompanyValidate implements Validator {
    @Autowired
    CompanyServiceImpl companyService;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Company company = (Company) target;

        if (company.getPhoneNumber().length() != 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.length", new String[]{"10"}, "Số điện thoại phải có 10 chữ số");
        } else if (!company.getPhoneNumber().startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneNumber.start", "Số điện thoại phải bắt đầu bằng số 0");
        }

        if (company.getUser() != null) {
            List<Company> companyList = companyService.findCompanyListByUser(company.getUser().getId());
            if (companyList != null && companyList.size() != 0) {
                errors.rejectValue("user", "user.owner", "Người dùng ID = " + company.getUser().getId() + " - " + company.getUser().getUsername() + " đã có sân bóng");
            }
        }
        else {
            errors.rejectValue("user", "user.exist", "Không tồn tại người dùng - Nhập ID người dùng để xác định chủ sân bóng");
        }

    }
}
