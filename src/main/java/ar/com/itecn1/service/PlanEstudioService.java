package ar.com.itecn1.service;

import ar.com.itecn1.model.PlanEstudio;
import java.util.List;

public interface PlanEstudioService extends CRUDService<PlanEstudio> {
    PlanEstudio findByName(String nombre);
    List<PlanEstudio> buscarPlanes(String texto);
    List<PlanEstudio> findActivos();
    List<PlanEstudio> findInactivos();
    List<PlanEstudio> findPlanesPorAnio(int anio);
    void reactivarPlan(String nombre);
    void agregarMateria(String nombrePlan, String codigoMateria);
    void quitarMateria(String nombrePlan, String codigoMateria);
}