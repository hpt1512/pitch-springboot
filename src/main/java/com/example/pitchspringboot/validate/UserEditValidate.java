package com.example.pitchspringboot.validate;

import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEditValidate implements Validator {
    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getPhoneNumber().length() != 10) {
            errors.rejectValue("phoneNumber", "phoneNumber.length", new String[]{"10"}, "Số điện thoại phải có 10 chữ số");
        } else if (!user.getPhoneNumber().startsWith("0")) {
            errors.rejectValue("phoneNumber", "phoneNumber.start", "Số điện thoại phải bắt đầu bằng số 0");
        }

    }
}
