package ar.com.itecn1.controller;

import ar.com.itecn1.model.PlanEstudio;
import ar.com.itecn1.service.PlanEstudioService;
import ar.com.itecn1.service.impl.PlanEstudioServiceImpl;

import java.util.List;

public class PlanEstudioController {

    private final PlanEstudioService planEstudioService;

    public PlanEstudioController() {
        this.planEstudioService = new PlanEstudioServiceImpl();
    }

    public PlanEstudio findByName(String nombre) {
        return planEstudioService.findByName(nombre);
    }

    public List<PlanEstudio> findAll() {
        return planEstudioService.findAll();
    }

    public List<PlanEstudio> buscarPlanes(String texto) {
        return planEstudioService.buscarPlanes(texto);
    }

    public void crearPlanEstudio(PlanEstudio planEstudio) {
        planEstudioService.save(planEstudio);
    }

    public void editarPlanEstudio(PlanEstudio planEstudio) {
        planEstudioService.update(planEstudio);
    }

    public void eliminarPlanEstudio(PlanEstudio planEstudio) {
        planEstudioService.delete(planEstudio);
    }

    public void reactivarPlanEstudio(String nombre) {
        planEstudioService.reactivarPlan(nombre);
    }

}