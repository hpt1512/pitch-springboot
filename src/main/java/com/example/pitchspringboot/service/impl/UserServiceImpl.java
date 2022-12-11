package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.IUserRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IBaseService<User> {
    @Autowired
    IUserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}
