package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Role;
import com.example.pitchspringboot.repositoty.IRoleRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IBaseService<Role> {
    @Autowired
    IRoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void insert(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findByName(String name) {
        return null;
    }
}
