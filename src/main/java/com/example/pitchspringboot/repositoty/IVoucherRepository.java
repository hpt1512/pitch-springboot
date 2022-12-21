package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVoucherRepository extends JpaRepository<Voucher, Integer> {
}
