package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<Booking, Integer> {
}
