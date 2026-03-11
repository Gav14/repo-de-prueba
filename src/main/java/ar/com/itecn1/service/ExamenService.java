package ar.com.itecn1.service;

import ar.com.itecn1.model.Examen;

public interface ExamenService extends CRUDService<Examen> {
    Examen findById(String idExamen);
}
