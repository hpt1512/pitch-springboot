package com.example.pitchspringboot.validate;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Pitch;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PitchValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pitch pitch = (Pitch) target;

        if (pitch.getPeopleMax() != null) {
            if (pitch.getPeopleMax() != 5 && pitch.getPeopleMax() != 7) {
                errors.rejectValue("peopleMax", "pitch.peopleMax", "Số người trên sân chỉ được là 5 hoặc 7");
            }
        }

    }
}
