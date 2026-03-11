package ar.com.itecn1.repository;

import ar.com.itecn1.model.ComisionMateria;

public interface ComisionRepository extends CRUDRepository<ComisionMateria> {
    ComisionMateria findByCode(String codigoComision);
}
