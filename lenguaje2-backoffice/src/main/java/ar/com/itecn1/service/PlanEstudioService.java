package ar.com.itecn1.service;

import ar.com.itecn1.model.PlanEstudio;

public interface PlanEstudioService extends CRUDService<PlanEstudio> {
    PlanEstudio findByName(String nombre);
}
