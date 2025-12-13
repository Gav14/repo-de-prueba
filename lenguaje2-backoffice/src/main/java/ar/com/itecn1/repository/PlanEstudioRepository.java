package ar.com.itecn1.repository;

import ar.com.itecn1.model.PlanEstudio;

public interface PlanEstudioRepository extends CRUDRepository<PlanEstudio> {
    PlanEstudio findByName(String nombre);
}
