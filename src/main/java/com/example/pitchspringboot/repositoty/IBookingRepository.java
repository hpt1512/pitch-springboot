package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findBookingByUser(User user);
    List<Booking> findBookingByPitchAndDateAndTime(Pitch pitch, Date date, String time);
    @Modifying
    @Transactional
    @Query(value="update booking set status = 1 where id = ?;", nativeQuery=true)
    void confirmBooking(Integer idBooking);
    @Modifying
    @Transactional
    @Query(value="update booking set status = 2 where id = ?;", nativeQuery=true)
    void declineBooking(Integer idBooking);
}
