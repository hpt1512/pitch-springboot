package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.Location;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.ICompanyRepository;
import com.example.pitchspringboot.repositoty.IRepostCompany;
import com.example.pitchspringboot.repositoty.ITopCompany;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements IBaseService<Company> {
    @Autowired
    private ICompanyRepository companyRepository;
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void insert(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void update(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Company findById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findByName(String name) {
        return companyRepository.findCompaniesByNameContaining(name);
    }
    public List<Company> findByLocation(Location location) {
        return companyRepository.findCompaniesByLocation(location);
    }
    public Company findCompanyByUser(User user) {
        return companyRepository.findCompaniesByUser(user);
    }
    public List<Company> findCompanyListByUser(Integer id) {
        return companyRepository.findCompanyListByUser(id);
    }
    public Integer countAllCompany() {
        return companyRepository.countAllCompany();
    }
    public List<IRepostCompany> getReportCompany() {
        return companyRepository.getReportCompany();
    }
    public List<ITopCompany> getTopCompany() {
        return companyRepository.getTopCompany();
    }
}
