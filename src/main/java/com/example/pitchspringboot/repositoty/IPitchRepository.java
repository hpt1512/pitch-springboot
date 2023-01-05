package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPitchRepository extends JpaRepository<Pitch, Integer> {
    List<Pitch> findPitchByCompany(Company company);
}
