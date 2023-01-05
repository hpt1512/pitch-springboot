package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByFullNameContaining(String name);
    List<User> findUserByUsername(String username);
    @Modifying
    @Transactional
    @Query(value="update `user` set `password` = ?1 where id = ?2;", nativeQuery=true)
    void changePassword(String newPassword, Integer id);
}
