package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.model.Voucher;
import com.example.pitchspringboot.repositoty.IBookingRepository;
import com.example.pitchspringboot.repositoty.IReportIncomeByMonth;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements IBaseService<Booking> {
    @Autowired
    IBookingRepository bookingRepository;
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void insert(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public Booking findById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findByName(String name) {
        return null;
    }
    public List<Booking> findByUser(User user) {
        return bookingRepository.findBookingByUser(user);
    }
    public List<Booking> findByPitchAndDateAndTime(Pitch pitch, Date date, String time) {
        return bookingRepository.findBookingByPitchAndDateAndTime(pitch, date, time);
    }
    public void confirmBooking(Integer id) {
        bookingRepository.confirmBooking(id);
    }
    public void declineBooking(Integer id) {
        bookingRepository.declineBooking(id);
    }
    public void confirmPay(Integer id) {
        bookingRepository.confirmPay(id);
    }
    public List<Booking> findByPitchDateTimeCustoms(String pitchFindId, String datefind, String timeFind, String statusFind) {
        return bookingRepository.findByPitchDateTimeCustoms(pitchFindId, datefind, timeFind, statusFind);
    }
    public List<Booking> findByUserAndStatus(User user, Integer status) {
        return bookingRepository.findBookingByUserAndStatus(user, status);
    }
    public List<Booking> findByUserAndStatusAndVoucher(User user, Integer status, Voucher voucher) {
        return bookingRepository.findBookingByUserAndStatusAndVoucher(user, status, voucher);
    }
    public List<IReportIncomeByMonth> reportIncomeByMonth(Integer id) {
        return bookingRepository.reportIncomeByMonth(id);
    }
    public List<Booking> findByPitch(Pitch pitch) {
        return bookingRepository.findBookingByPitch(pitch);
    }
}
