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
    @Query(value = "select * from company where id_user = ?;", nativeQuery = true)
    List<Company> findCompanyListByUser(Integer id);
    @Query(value = "select count(id) from company;", nativeQuery = true)
    Integer countAllCompany();
    @Query(value = "select * from v_report_company order by amount desc;", nativeQuery = true)
    List<IRepostCompany> getReportCompany();
    @Query(value = "select * from v_top_company order by countBooking desc limit 4;", nativeQuery = true)
    List<ITopCompany> getTopCompany();
}
