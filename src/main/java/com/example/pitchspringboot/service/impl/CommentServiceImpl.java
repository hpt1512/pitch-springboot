package com.example.pitchspringboot.service.impl;

import com.example.pitchspringboot.model.Comment;
import com.example.pitchspringboot.model.Company;
import com.example.pitchspringboot.model.User;
import com.example.pitchspringboot.repositoty.ICommentRepository;
import com.example.pitchspringboot.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements IBaseService<Comment> {
    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void insert(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findByName(String name) {
        return null;
    }
    public List<Comment> findCommentByCompany(Company company) {
        return commentRepository.findCommentByCompany(company);
    }
    public Integer countCommentByCompany(Company company) {
        return commentRepository.countCommentByCompany(company);
    }
    public void updateContentById(String content, Integer id) {
        commentRepository.updateContentById(content, id);
    }
    public List<Comment> findByUser(User user) {
        return commentRepository.findCommentByUser(user);
    }
}
