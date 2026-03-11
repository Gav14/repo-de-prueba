package ar.com.itecn1.repository;

import ar.com.itecn1.model.Profesor;

public interface ProfesorRepository extends CRUDRepository<Profesor>{
    Profesor findByDni(String dni);
}
