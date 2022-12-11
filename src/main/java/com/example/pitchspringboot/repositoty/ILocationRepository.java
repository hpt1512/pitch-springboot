package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<Location, Integer> {
}
