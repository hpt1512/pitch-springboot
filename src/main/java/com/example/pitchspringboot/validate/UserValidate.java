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
    }
}
