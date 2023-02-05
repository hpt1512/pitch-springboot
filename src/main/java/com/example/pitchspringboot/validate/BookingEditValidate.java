package com.example.pitchspringboot.validate;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.service.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class BookingEditValidate implements Validator {
    @Autowired
    BookingServiceImpl bookingService;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Booking booking = (Booking) target;
        Date date = new Date();
        Date dateEnd;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        dateEnd = c.getTime();

        if (booking.getDate() == null) {
            errors.rejectValue("date","date.validate.null",null,"Bạn chưa chọn ngày");
        }
        else {
            if(date.after(booking.getDate())) {
                errors.rejectValue("date","date.validate.start",null,"Ngày đặt sân không được trước ngày hiện tại");
            }
            if(booking.getDate().after(dateEnd)) {
                errors.rejectValue("date","date.validate.end",null,"Ngày đặt sân không được cách ngày hiện tại quá 1 tuần");
            }
        }

        List<Booking> bookingCheck = bookingService.findByPitchAndDateAndTime(booking.getPitch(), booking.getDate(), booking.getTime());


        if (bookingCheck.size() != 0) {
            if (bookingCheck.get(0).getId() != booking.getId()) {
                errors.rejectValue("time","time.validate",null,"Đã có người đặt");
            }
        }

    }
}
