package ar.com.itecn1.service;

import ar.com.itecn1.model.Profesor;

public interface ProfesorService extends CRUDService<Profesor>{
    Profesor findByDni(String dni);
}
