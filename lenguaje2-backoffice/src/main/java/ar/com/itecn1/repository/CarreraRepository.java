package ar.com.itecn1.repository;

import ar.com.itecn1.model.Carrera;

public interface CarreraRepository extends CRUDRepository<Carrera> {
    Carrera findByName(String nombre);
}
