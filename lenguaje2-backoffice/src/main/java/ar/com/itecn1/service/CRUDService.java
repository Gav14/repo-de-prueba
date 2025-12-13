package ar.com.itecn1.service;

import java.util.List;

public interface CRUDService <T>{
    List<T> findAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}
