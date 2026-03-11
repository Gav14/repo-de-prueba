package ar.com.itecn1.service;

import ar.com.itecn1.model.AlumnoInscriptoCarrera;

public interface AlumnoInscriptoCarreraService extends CRUDService<AlumnoInscriptoCarrera> {
    AlumnoInscriptoCarrera findByDni(String dni);
}
