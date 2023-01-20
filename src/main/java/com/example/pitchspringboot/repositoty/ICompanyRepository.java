package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Location;
import com.example.pitchspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findCompaniesByNameContaining(String name);
    List<Company> findCompaniesByLocation(Location location);
    Company findCompaniesByUser(User user);
    @Query(value = "select count(id) from company;", nativeQuery = true)
    Integer countAllCompany();
    @Query(value = "select * from v_report_company order by amount desc;", nativeQuery = true)
    List<IRepostCompany> getReportCompany();
}
