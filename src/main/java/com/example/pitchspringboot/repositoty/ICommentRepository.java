package com.example.pitchspringboot.repositoty;

import com.example.pitchspringboot.model.Comment;
import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ICommentRepository  extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentByCompany(Company company);
    Integer countCommentByCompany(Company company);
    List<Comment> findCommentByUser(User user);
    @Modifying
    @Transactional
    @Query(value="update comment set content = ? where id = ?;", nativeQuery=true)
    void updateContentById(String content, Integer id);
}
