package ar.com.itecn1.service;

import ar.com.itecn1.model.Alumno;

public interface AlumnoService extends CRUDService<Alumno> {
    Alumno findByDni(String dni);
}
