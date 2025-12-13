package ar.com.itecn1.service;

import ar.com.itecn1.model.AlumnoInscriptoMateria;

public interface AlumnoInscriptoMateriaService extends CRUDService<AlumnoInscriptoMateria> {
    AlumnoInscriptoMateria findByAlumnoDni(String dni);
}
