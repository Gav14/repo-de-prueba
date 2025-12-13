package ar.com.itecn1.service;

import ar.com.itecn1.model.Cuatrimestre;

public interface CuatrimestreService extends CRUDService<Cuatrimestre> {
    Cuatrimestre findByNumber(String numeroCuatrimestre);
}
