package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Booking;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.model.Voucher;
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
    @Query(value="select * from booking where id_pitch like concat(\"%\" , ? , \"%\") and date like concat(\"%\" , ? , \"%\") and time like concat(\"%\" , ? , \"%\") and status like concat(\"%\" , ? , \"%\");", nativeQuery=true)
    List<Booking> findByPitchDateTimeCustoms(String pitchFindId, String datefind, String timeFind, String statusFind);
    @Modifying
    @Transactional
    @Query(value="update booking set status = 1 where id = ?;", nativeQuery=true)
    void confirmBooking(Integer idBooking);
    @Modifying
    @Transactional
    @Query(value="update booking set status = 2 where id = ?;", nativeQuery=true)
    void declineBooking(Integer idBooking);
    @Modifying
    @Transactional
    @Query(value="update booking set status = 3 where id = ?;", nativeQuery=true)
    void confirmPay(Integer idBooking);
    List<Booking> findBookingByUserAndStatus(User user, Integer status);
    List<Booking> findBookingByUserAndStatusAndVoucher(User user, Integer status, Voucher voucher);
    @Query(value = "select id, month, year, sum from v_report_income where id = ?;", nativeQuery = true)
    List<IReportIncomeByMonth> reportIncomeByMonth(Integer id);
    List<Booking> findBookingByPitch(Pitch pitch);
}
