package ar.com.itecn1.service;

import ar.com.itecn1.model.ComisionMateria;

public interface ComisionService extends CRUDService<ComisionMateria> {
    ComisionMateria findByCode(String codigoMateria);
}
