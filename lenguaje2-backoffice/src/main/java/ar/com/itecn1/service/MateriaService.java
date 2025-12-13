package ar.com.itecn1.service;

import ar.com.itecn1.model.Materia;

public interface MateriaService extends CRUDService<Materia> {
    Materia findByCode(String codigoMateria);
}
