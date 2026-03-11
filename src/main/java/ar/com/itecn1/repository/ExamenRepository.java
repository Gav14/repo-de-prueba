package ar.com.itecn1.repository;

import ar.com.itecn1.model.Examen;

public interface ExamenRepository extends CRUDRepository<Examen> {
    Examen findById(String idExamen);
}
