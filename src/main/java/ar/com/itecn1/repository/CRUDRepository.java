package ar.com.itecn1.repository;

import java.util.List;

public interface CRUDRepository<T> {
    List<T> findAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}