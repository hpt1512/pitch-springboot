package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Voucher;
import com.example.pitchspringboot.repositoty.IVoucherRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements IBaseService<Voucher> {
    @Autowired
    IVoucherRepository voucherRepository;
    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public void insert(Voucher voucher) {

    }

    @Override
    public void update(Voucher voucher) {

    }

    @Override
    public void delete(Voucher voucher) {

    }

    @Override
    public Voucher findById(int id) {
        return voucherRepository.findById(id).orElse(null);
    }

    @Override
    public List<Voucher> findByName(String name) {
        return null;
    }
}
