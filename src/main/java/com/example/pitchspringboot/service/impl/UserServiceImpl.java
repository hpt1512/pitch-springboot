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

    @Override
    public List<User> findByName(String name) {
        return userRepository.findUserByFullNameContaining(name);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void changePassword(String newPassword, Integer id) {
        userRepository.changePassword(newPassword, id);
    }
    public void activateAccount(Integer id) {
        userRepository.activateAccount(id);
    }
    public void raisePoint(Integer id) {
        userRepository.raisePoint(id);
    }
    public Integer countAllUser() {
        return userRepository.countAllUser();
    }
    public List<User> reportUser() {
        return userRepository.reportUser();
    }
    public void addRoleOwner(Integer id) {
        userRepository.addRoleOwner(id);
    }
    public void removeRoleOwner(Integer id) {
        userRepository.removeRoleOwner(id);
    }
}
