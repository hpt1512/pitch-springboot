package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.repositoty.IBookingRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
