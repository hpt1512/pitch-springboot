package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
}
