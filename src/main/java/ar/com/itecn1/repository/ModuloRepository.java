package ar.com.itecn1.repository;

import ar.com.itecn1.model.Modulo;

public interface ModuloRepository extends CRUDRepository<Modulo> {
    Modulo findByCode(String codigoModulo);
}
