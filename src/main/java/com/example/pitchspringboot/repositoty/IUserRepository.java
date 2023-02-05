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
    @Query(value="update `user` set `password` = ? where id = ?;", nativeQuery=true)
    void changePassword(String newPassword, Integer id);
    @Modifying
    @Transactional
    @Query(value="update `user` set `status` = 1 where id = ?;", nativeQuery=true)
    void activateAccount(Integer id);
    @Modifying
    @Transactional
    @Query(value="update `user` set `point` = `point` + 1 where id = ?;", nativeQuery=true)
    void raisePoint(Integer id);
    @Query(value = "select count(id) from user where status = 1 and id_role != 1;", nativeQuery = true)
    Integer countAllUser();
    @Query(value = "select * from user where id_role = 2 and status = 1 order by point desc limit 5;", nativeQuery = true)
    List<User> reportUser();
    @Modifying
    @Transactional
    @Query(value="update `user` set `id_role` = 3 where id = ?;", nativeQuery=true)
    void addRoleOwner(Integer id);
    @Modifying
    @Transactional
    @Query(value="update `user` set `id_role` = 2 where id = ?;", nativeQuery=true)
    void removeRoleOwner(Integer id);
}
