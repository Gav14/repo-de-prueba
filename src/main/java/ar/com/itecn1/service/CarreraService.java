package ar.com.itecn1.service;

import ar.com.itecn1.model.Carrera;
import ar.com.itecn1.model.PlanEstudio;

public interface CarreraService extends CRUDService<Carrera> {
    Carrera findByName(String nombre);
}
