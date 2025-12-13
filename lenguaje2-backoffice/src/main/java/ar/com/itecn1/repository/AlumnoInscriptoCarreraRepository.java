package ar.com.itecn1.repository;

import ar.com.itecn1.model.AlumnoInscriptoCarrera;

public interface AlumnoInscriptoCarreraRepository extends CRUDRepository<AlumnoInscriptoCarrera> {
    AlumnoInscriptoCarrera findByDni(String dni);
}
