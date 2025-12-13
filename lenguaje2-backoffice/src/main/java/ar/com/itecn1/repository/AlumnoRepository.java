package ar.com.itecn1.repository;

import ar.com.itecn1.model.Alumno;

public interface AlumnoRepository extends CRUDRepository<Alumno> {
    Alumno findByDni(String dni);
}
