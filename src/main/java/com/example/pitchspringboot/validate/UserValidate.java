package com.example.pitchspringboot.validate;

import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidate implements Validator {
    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByUsername(user.getUsername()).size() != 0) {
            errors.rejectValue("username","username.validate",null,"Tên tài khoản đã tồn tại");
        }

        if (user.getPhoneNumber().length() != 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.length", new String[]{"10"}, "Số điện thoại phải có 10 chữ số");
        } else if (!user.getPhoneNumber().startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneNumber.start", "Số điện thoại phải bắt đầu bằng số 0");
        }

    }
}
