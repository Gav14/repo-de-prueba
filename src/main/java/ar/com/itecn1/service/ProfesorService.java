package ar.com.itecn1.service;

import ar.com.itecn1.model.Profesor;

import java.util.List;

public interface ProfesorService extends CRUDService<Profesor> {
    Profesor findByDni(String dni);
}