package ar.com.itecn1.repository;

import ar.com.itecn1.model.Materia;

public interface MateriaRepository extends CRUDRepository<Materia> {
    Materia findByCode(String codigoMateria);
}
