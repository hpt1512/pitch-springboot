package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPitchRepository extends JpaRepository<Pitch, Integer> {
}
