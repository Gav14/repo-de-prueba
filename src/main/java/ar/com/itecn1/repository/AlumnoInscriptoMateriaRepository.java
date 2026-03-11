package ar.com.itecn1.repository;

import ar.com.itecn1.model.AlumnoInscriptoMateria;

public interface AlumnoInscriptoMateriaRepository extends CRUDRepository<AlumnoInscriptoMateria> {
    AlumnoInscriptoMateria findByAlumnoDni(String dni);
}
