package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Location;
import com.example.pitchspringboot.repositoty.ILocationRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements IBaseService<Location> {
    @Autowired
    ILocationRepository locationRepository;
    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public void insert(Location location) {

    }

    @Override
    public void update(Location location) {

    }

    @Override
    public void delete(Location location) {

    }

    @Override
    public Location findById(int id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> findByName(String name) {
        return null;
    }
}
