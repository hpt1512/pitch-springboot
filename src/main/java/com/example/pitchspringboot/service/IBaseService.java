package com.example.pitchspringboot.service;

import java.util.List;

public interface IBaseService<E> {
    List<E> findAll();
    void insert(E e);
    void update(E e);
    void delete(E e);
    E findById(int id);
}
