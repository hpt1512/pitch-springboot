package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findCompaniesByNameContaining(String name);
    List<Company> findCompaniesByLocation(Location location);
}
