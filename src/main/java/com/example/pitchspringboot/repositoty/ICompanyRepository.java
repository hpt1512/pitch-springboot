package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
}
