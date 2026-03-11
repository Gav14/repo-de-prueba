package ar.com.itecn1.service;

import ar.com.itecn1.model.Alumno;

import java.util.List;

public interface AlumnoService extends CRUDService<Alumno> {
    Alumno findByDni(String dni);
    List<Alumno> findAll();
    void save(Alumno alumno);
    void update(Alumno alumno);
    void delete(Alumno alumno);
    boolean validarFormatoDni(String dni);
}
