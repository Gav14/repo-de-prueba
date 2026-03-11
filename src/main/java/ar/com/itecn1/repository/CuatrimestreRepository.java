package ar.com.itecn1.repository;

import ar.com.itecn1.model.Cuatrimestre;

public interface CuatrimestreRepository extends CRUDRepository<Cuatrimestre> {
    Cuatrimestre findByNumber(String numeroCuatrimestre);
}
