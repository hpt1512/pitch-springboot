package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
