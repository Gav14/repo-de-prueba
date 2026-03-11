package ar.com.itecn1.repository;

import ar.com.itecn1.model.PlanEstudio;
import java.util.List;

public interface PlanEstudioRepository extends CRUDRepository<PlanEstudio> {
    PlanEstudio findByName(String nombre);
    List<PlanEstudio> findByNombreSimilar(String texto);
    List<PlanEstudio> findByActivo(boolean activo);
    List<PlanEstudio> findByAnio(int anio);
}