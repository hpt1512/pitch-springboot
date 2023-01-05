package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Pitch;
import com.example.pitchspringboot.repositoty.IPitchRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PitchServiceImpl implements IBaseService<Pitch> {
    @Autowired
    IPitchRepository pitchRepository;
    @Override
    public List<Pitch> findAll() {
        return pitchRepository.findAll();
    }

    @Override
    public void insert(Pitch pitch) {
        pitchRepository.save(pitch);
    }

    @Override
    public void update(Pitch pitch) {
        pitchRepository.save(pitch);
    }

    @Override
    public void delete(Pitch pitch) {
        pitchRepository.delete(pitch);
    }

    @Override
    public Pitch findById(int id) {
        return pitchRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pitch> findByName(String name) {
        return null;
    }
    public List<Pitch> findByCompany(Company company) {
        return pitchRepository.findPitchByCompany(company);
    }
}
