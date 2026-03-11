package ar.com.itecn1.service;

import ar.com.itecn1.model.Modulo;

public interface ModuloService extends CRUDService<Modulo> {
    Modulo findByCode(String codigoModulo);
}
